package com.example.domains.service;

import com.example.domains.exception.ResourceNotFoundException;
import com.example.domains.model.Domain;
import com.example.domains.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Service
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/domains")
public class DomainService {


    private final DomainRepository domainRepository;

    @GetMapping("{domainName}/{code}")
    Mono<Domain> getDomainByDomainNameAndCode(@PathVariable @NotBlank(message = "domain name should not be blank") String domainName,
                                              @PathVariable @NotBlank(message = "domain code should not be blank") String code) {

        return domainRepository.findDomainByDomainNameAndCode(domainName, code)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("domain with code " +code+" and domain name "+ domainName +" not found")));
    }


    @PostMapping
    Mono<Domain> createDomain(@RequestBody @Valid Domain domain){

        return domainRepository.insert(domain);

    }

}
