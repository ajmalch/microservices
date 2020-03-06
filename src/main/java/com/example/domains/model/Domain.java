package com.example.domains.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@Document
//@NoArgsConstructor
@JsonDeserialize(builder = Domain.DomainBuilder.class)
public class Domain {

    @Id
    private String domainId;
    @NotBlank(message = "code should not be blank for domain")
    private String code;
    @NotBlank(message = "domainName should not be blank")
    private String domainName;
    @NotBlank(message = "description should not be blank for domain")
    private String description;

}
