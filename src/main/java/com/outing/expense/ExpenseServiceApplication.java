package com.outing.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@SpringBootApplication(scanBasePackages = {"com.outing.auth","com.outing.expense","com.outing.commons"})
@EnableFeignClients(basePackages = "com.outing.friendship")
public class ExpenseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseServiceApplication.class,args);
        System.out.println("Expense Service Started");
    }

    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestCustomizer(){
        return (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry)->{
            registry
                    .requestMatchers("/expense-service/**").permitAll()
                    .anyRequest().authenticated();
        };
    }
}