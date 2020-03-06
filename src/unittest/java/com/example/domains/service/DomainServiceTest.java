package com.example.domains.service;

import com.example.domains.exception.ResourceNotFoundException;
import com.example.domains.model.Domain;
import com.example.domains.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@DirtiesContext
public class DomainServiceTest {

    @InjectMocks
    private DomainService domainService;

    @Mock
    private DomainRepository domainRepository;

    private static final Domain domain = Domain.builder()
            .domainName("domain")
            .description("One")
            .build();


    @Test
    void getDomainByDomainNameAndCodeShouldReturnDomainMonoWhenRepositoryGivesBackDomain() {

        Mockito.when(domainRepository.findDomainByDomainNameAndCode(
                Mockito.eq("domain"),
                Mockito.eq("01")))
                .thenReturn(Mono.just(domain));

        Mono<Domain> domainMono = domainService.getDomainByDomainNameAndCode("domain","01");

        StepVerifier.create(domainMono)
                .expectNextMatches(domain -> "One".equalsIgnoreCase(domain.getDescription()))
                .verifyComplete();

    }

    @Test
    void getDomainByDomainNameAndShouldThrowExceptionWhenRepositoryGivesBackEmpty(){

        Mockito.when(domainRepository.findDomainByDomainNameAndCode(
                Mockito.eq("domain"),
                Mockito.eq("notfound")))
                .thenReturn(Mono.empty());

        Mono<Domain> domainMono = domainService.getDomainByDomainNameAndCode("domain","notfound");

        StepVerifier.create(domainMono)
                .expectErrorMatches(e-> e instanceof ResourceNotFoundException
                        && e.getMessage().equals("domain with code notfound and domain name domain not found") )
                .verify();


    }

}
