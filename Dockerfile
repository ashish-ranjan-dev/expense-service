FROM openjdk:17
EXPOSE 8085
ADD ./build/libs/expense-service-1.0-SNAPSHOT.jar expense-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","expense-service-1.0-SNAPSHOT.jar"]
