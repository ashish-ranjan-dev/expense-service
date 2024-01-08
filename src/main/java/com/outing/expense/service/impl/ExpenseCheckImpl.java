package com.outing.expense.service.impl;

import com.netflix.discovery.converters.Auto;
import com.outing.auth.security.util.PrincipalDetails;
import com.outing.commons.api.exception.OutingException;
import com.outing.expense.api.dto.ExpenseDto;
import com.outing.expense.api.dto.ExpenseShareDto;
import com.outing.expense.api.enums.Constants;
import com.outing.expense.model.ExpenseModel;
import com.outing.expense.model.ExpenseShareModel;
import com.outing.expense.repository.ExpenseRepository;
import com.outing.expense.repository.ExpenseShareRepository;
import com.outing.expense.service.ExpenseCheckService;
import com.outing.friendship.api.controller.FriendshipCheckController;
import com.outing.friendship.api.dto.FriendshipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExpenseCheckImpl implements ExpenseCheckService {

    @Autowired
    PrincipalDetails principalDetails;

    @Autowired
    ExpenseShareRepository expenseShareRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    FriendshipCheckController friendshipCheckController;

    public List<ExpenseShareModel> getExpenseShareByExpenseId(String expenseId) {
        List<ExpenseShareModel> expenseShareModel = expenseShareRepository.findByExpenseId(expenseId);
        if (expenseShareModel == null) {
            throw new OutingException(Constants.INVALID_EXPENSE, HttpStatus.NOT_FOUND);
        }
        return expenseShareModel;
    }

    public ExpenseModel getExpenseById(String expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new OutingException(Constants.INVALID_EXPENSE, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ExpenseDto> getAllExpensesById(String expenseIdd) {
//        String loggedInUserId = principalDetails.getPrincipalDetails().getId();
        System.out.println(expenseIdd);
        Set<String> uniqueExpenseIds = new HashSet<>();

        List<ExpenseShareModel> expenseShareModels = expenseShareRepository.findByExpenseIdAndExpenseNotDeleted(expenseIdd);
        System.out.println(expenseShareModels);
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        int sz = 0;
        for (ExpenseShareModel expenseShareModel : expenseShareModels) {
            String expenseId = expenseShareModel.getExpenseId();
            if (uniqueExpenseIds.contains(expenseId)) {
                continue;
            }
            uniqueExpenseIds.add(expenseId);
            sz++;
            ExpenseModel expenseModel = getExpenseById(expenseId);
            List<ExpenseShareModel> expenseShareModels1 = getExpenseShareByExpenseId(expenseId);
            List<String> friendsId = new ArrayList<>();
            List<Integer> expenseShare = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Integer> paidAmount = new ArrayList<>();
            List<String> approvalStatus = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            List<String> expenseIds = new ArrayList<>();
            for (ExpenseShareModel expenseShareModel1 : expenseShareModels1) {
                ids.add(expenseShareModel1.getId());
                expenseIds.add(expenseShareModel1.getExpenseId());
                friendsId.add(expenseShareModel1.getUserId());
                paidAmount.add(expenseShareModel1.getBearerAmount());
                expenseShare.add(expenseShareModel1.getBeneficiaryAmount());
                names.add(expenseShareModel1.getName());
                approvalStatus.add(expenseShareModel1.getStatus());
            }
            ExpenseShareDto[] expenseShareDto = new ExpenseShareDto[friendsId.size()];
            for (int i = 0; i < friendsId.size(); i++) {
                expenseShareDto[i] = new ExpenseShareDto();
                expenseShareDto[i].setId(ids.get(i));
                expenseShareDto[i].setExpenseId(expenseIds.get(i));
                expenseShareDto[i].setUserId(friendsId.get(i));
                expenseShareDto[i].setBearerAmount(paidAmount.get(i));
                expenseShareDto[i].setBeneficiaryAmount(expenseShare.get(i));
                expenseShareDto[i].setName(names.get(i));
                expenseShareDto[i].setStatus(approvalStatus.get(i));
            }
//            Arrays.sort(expenseShareDto, Comparator.comparing(dto -> dto.getStatus()));
            Arrays.sort(expenseShareDto, (dto1, dto2) -> {
                // Custom comparator to prioritize "invited" > "rejected" > "accepted"
                String status1 = dto1.getStatus();
                String status2 = dto2.getStatus();

                if (status1.equals(status2)) {
                    return 0; // Both have the same status
                } else if (status1.equals("invited")) {
                    return -1; // "invited" comes before "rejected" and "accepted"
                } else if (status1.equals("rejected") && status2.equals("accepted")) {
                    return -1; // "rejected" comes before "accepted"
                } else {
                    return 1; // "accepted" comes after "rejected" and "invited"
                }
            });
            ExpenseDto expenseDto = new ExpenseDto(expenseModel.getId(), expenseModel.getDescription(), expenseModel.getTotalAmount(), expenseModel.getDate(), expenseModel.getCreatorId(), expenseShareDto);
            expenseDtos.add(expenseDto);

        }
        return expenseDtos;
    }

    private boolean isNotFriendWithCurrentLoggedInUser(List<FriendshipDto> friends, String friendId) {
        for(FriendshipDto friend:friends){
            if(friend.getInviteeUser()==null) {
                if (Objects.equals(friend.getDummyUser().getId(), friendId) || Objects.equals(friend.getInviterUser().getId(), friendId)) {
                    return false;
                }
            }
            else {
                if (Objects.equals(friend.getInviteeUser().getId(), friendId) || Objects.equals(friend.getInviterUser().getId(), friendId)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<String> amountCheck(int totalAmount, int totPaidAmount, int totSharAmount) {
        List<String> errors = new ArrayList<>();
        if(totSharAmount!=totPaidAmount || totPaidAmount!=totalAmount){
            if(totSharAmount!=totPaidAmount) {
                errors.add(Constants.INVALID_SHARE_PAID_AMOUNT);
            }
            else{
                errors.add(Constants.INVALID_TOTAL_SHARE_AMOUNT);
            }
        }
        return errors;
    }


    public ExpenseDto addExpense(ExpenseDto expenseDto, String expenseIdd, String loggedInUserIdd) {
        System.out.println("---" + expenseDto.getExpenseDetails() + "---" + expenseIdd + "---" + loggedInUserIdd);
        String loggedInUserId = loggedInUserIdd;
        List<String> friendsId = new ArrayList<>();
        ExpenseDto newExpenseDto = new ExpenseDto();
        ExpenseShareDto expenseShareDto[] = expenseDto.getExpenseDetails();
        for(int i=0;i<expenseShareDto.length;i++){
            friendsId.add(expenseShareDto[i].getUserId());
        }
        List<String> errors = new ArrayList<>();
        int entryNumber = 1;
        for(ExpenseShareDto expenseDetails:expenseDto.getExpenseDetails()){
            if(expenseDetails.getUserId()==null || expenseDetails.getUserId().isEmpty()){
                errors.add("User Id cannot be blank for entry "+entryNumber);
            }
        }
        List<FriendshipDto> friends = friendshipCheckController.getAllFriendsByUserId(loggedInUserId).getBody();
        if(friends==null || friends.isEmpty()){
            throw new OutingException("No friends",HttpStatus.NOT_FOUND);
        }
        int index = 0;
        for(String friendId:friendsId){
            if(Objects.equals(friendId, loggedInUserId))continue;
            if(isNotFriendWithCurrentLoggedInUser(friends, friendId)){
                throw new OutingException("User is not friend with id "+friendId+" name: "+expenseDto.getExpenseDetails()[index].getName(),HttpStatus.FORBIDDEN);
            }
            index++;
        }
        int totalAmount = expenseDto.getTotalAmount();
        String description = expenseDto.getDescription();
        if(description==null || description.isEmpty()){
            expenseDto.setDescription("Outing");
        }
        if(totalAmount==0){
            errors.add(Constants.INVALID_AMOUNT);
        }
        if(expenseDto.getExpenseDetails()==null || expenseDto.getExpenseDetails().length==0){
            errors.add(Constants.INVALID_FRIEND_LIST);
        }
        if(!errors.isEmpty()){
            throw new OutingException(errors,HttpStatus.FORBIDDEN);
        }
        String date = expenseDto.getDate();
        if(date==null || date.isEmpty())date = LocalDateTime.now().toString();
        String expenseId = expenseIdd;
        int totPaidAmount = 0;
        int totSharAmount = 0;
        List<String> expenseShareIds = new ArrayList<>();
        List<ExpenseShareModel> expenseShareModels = new ArrayList<>();
        for(int i=0;i<friendsId.size();i++){
            int paidAmount = expenseDto.getExpenseDetails()[i].getBearerAmount();
            int shareAmount = expenseDto.getExpenseDetails()[i].getBeneficiaryAmount();
            totPaidAmount+=paidAmount;
            totSharAmount+=shareAmount;
            String userId = expenseDto.getExpenseDetails()[i].getUserId();
            String expenseShareId = UUID.randomUUID().toString();
            String name = expenseDto.getExpenseDetails()[i].getName();
            expenseDto.getExpenseDetails()[i].setId(expenseShareId);
            expenseDto.getExpenseDetails()[i].setExpenseId(expenseId);
            expenseShareIds.add(expenseShareId);
            String approvalStatus = null;
            System.out.println(expenseDto.getExpenseDetails()[i].getStatus());
            if(expenseDto.getExpenseDetails()[i].getStatus()==null) {
                if (Objects.equals(userId, loggedInUserId)) {
                    approvalStatus = "accepted";
                } else approvalStatus = "invited";
            }
            else{
                approvalStatus = expenseDto.getExpenseDetails()[i].getStatus();
            }
            ExpenseShareModel expenseShareModel = new ExpenseShareModel(expenseShareId,expenseId,userId,paidAmount,shareAmount,approvalStatus,name);
            expenseShareModels.add(expenseShareModel);
        }
        errors = amountCheck(totalAmount, totPaidAmount, totSharAmount);
        if(!errors.isEmpty()){
            throw new OutingException(errors, HttpStatus.FORBIDDEN);
        }
        newExpenseDto.setId(expenseId);
        newExpenseDto.setDescription(description);
        newExpenseDto.setTotalAmount(totalAmount);
        newExpenseDto.setDate(date);
        newExpenseDto.setExpenseDetails(expenseDto.getExpenseDetails());
        newExpenseDto.setCreatorId(loggedInUserId);
        ExpenseModel expenseModel = new ExpenseModel(expenseId, date,totalAmount,description,false,loggedInUserId);
        expenseRepository.save(expenseModel);
        expenseShareRepository.saveAll(expenseShareModels);
        return newExpenseDto;
    }

}
