package com.demoproject.ems;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Electricity Management System",
        description = "Spring boot CRUD application for Electricity Management System",
        version = "1.0",
        contact = @Contact(name = "Madhur Singhal", email = "madhur.singhal@geminisolutions.com")))
public class ElectricityManagementSystemApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ElectricityManagementSystemApplication.class, args);
    }
}
