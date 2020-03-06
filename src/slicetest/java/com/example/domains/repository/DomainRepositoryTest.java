package com.example.domains.repository;

import com.example.domains.model.Domain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class DomainRepositoryTest {

    private final DomainRepository domainRepository;

    DomainRepositoryTest(@Autowired DomainRepository repository){
        domainRepository = repository;
    }

    private static final Domain domain = Domain.builder().domainName("domain").code("01").description("One").build();

    @Test
    void testFindDomainByDomainNameAndCode(){

        final var domainMono = domainRepository
                .deleteAll()
                .thenMany(domainRepository.insert(domain))
                .thenMany(domainRepository.findDomainByDomainNameAndCode("domain","01"));

        StepVerifier
                .create(domainMono)
                .expectNextMatches(d -> "One".equals(d.getDescription()))
                .verifyComplete();

    }


}
