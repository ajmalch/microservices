package com.example.domains.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
public class UserServiceTest {


    @Autowired
    private DomainService domainService;


    @Test
    void getDomainByDomainNameAndDescriptionShouldThrowExceptionWhenDomainNameNull(){

        assertThrows(ConstraintViolationException.class,
                () -> domainService.getDomainByDomainNameAndCode(null, "01"),
                "domain name should not be blank");
    }


}
