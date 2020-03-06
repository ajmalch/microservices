package com.example.domains.service;

import com.example.domains.model.Domain;
import com.example.domains.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@WebFluxTest
@Import(DomainService.class)
public class DomainServiceWebFluxTest {

    @MockBean
    private  DomainRepository domainRepository;


    private final WebTestClient webTestClient;

    public DomainServiceWebFluxTest(@Autowired WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void getDomainByDomainNameAndCodeTest(){

        Domain domain = Domain.builder()
                .domainName("domain")
                .code("01")
                .description("One")
                .build();

        when(domainRepository.findDomainByDomainNameAndCode(eq("domain"), eq("01")))
                .thenReturn(Mono.just(domain));

        webTestClient.get()
                .uri("/domains/domain/01")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.description")
                .isEqualTo("One");

    }


}
