package com.example.domains.repository;

import com.example.domains.model.Domain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DomainRepository extends ReactiveMongoRepository<Domain, String> {

    @Cacheable(value = "domainReferenceCode", key = "{#p0,#p1}")
    Mono<Domain> findDomainByDomainNameAndCode(String domainName,String code);

}
