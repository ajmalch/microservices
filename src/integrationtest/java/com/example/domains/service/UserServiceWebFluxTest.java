package com.example.domains.service;

import com.example.domains.model.Domain;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class UserServiceWebFluxTest {

//    @Autowired
//    @Qualifier("defaultValidator")
//    private Validator validator;
//
//    @InjectMocks
//    private DomainService domainService;
//
//    @Mock
//    private DomainRepository domainRepository;

//    public UserServiceTest(@Qualifier("defaultValidator") @Autowired Validator validator) {
//        this.validator = validator;
//    }

//    @Bean
//    public MethodValidationPostProcessor bean() {
//        return new MethodValidationPostProcessor();
//    }
//
//    @Test
//    void getDomainByDomainNameAndDescriptionShouldThrowExceptionWhenDomainNameNull(){
//
//        Mono<Domain> domainMono = domainService.getDomainByDomainNameAndCode(null,"01");
//
//        StepVerifier.create(domainMono)
//                .expectErrorMatches(e-> e instanceof ResourceNotFoundException
//                        && e.getMessage().equals("domain with code notfound and domain name domain not found") )
//                .verify();
//    }

    private final WebTestClient webTestClient;

    public UserServiceWebFluxTest(@Autowired WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }


    @Test
    void createDomainShouldReturn400ForInvalidInputBody(){

        webTestClient.post()
                .uri("/domains")
                .body(Mono.just(Domain.builder().build()), Domain.class)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.error")
                .isEqualTo("Bad Request")
                .jsonPath("$.message")
                .value(MatchesPattern.matchesPattern("^.*code should not be blank for domain.*$"));
    }
}
