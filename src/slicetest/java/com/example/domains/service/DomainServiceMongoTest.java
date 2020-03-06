package com.example.domains.service;

import com.example.domains.model.Domain;
import com.example.domains.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@DataMongoTest
@Import(DomainService.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext
class DomainServiceMongoTest {

    private final DomainService domainService;

    private final DomainRepository domainRepository;

    public DomainServiceMongoTest(@Autowired DomainRepository domainRepository, @Autowired DomainService domainService) {
        this.domainService = domainService;
        this.domainRepository = domainRepository;
    }

    private static final Domain domain = Domain.builder().domainName("domain").code("01").description("One").build();

    @Test
    void getDomainByDomainNameAndCode() {

        final var domainMono = domainRepository
                .deleteAll()
                .thenMany(domainRepository.save(domain))
                .flatMap(d->domainService.getDomainByDomainNameAndCode(d.getDomainName(),d.getCode()));

        StepVerifier
                .create(domainMono)
                .expectNextMatches(d-> "One".equals(d.getDescription()))
                .verifyComplete();

    }
}