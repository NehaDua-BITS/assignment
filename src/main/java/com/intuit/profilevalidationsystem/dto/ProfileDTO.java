package com.intuit.profilevalidationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intuit.profilevalidationsystem.model.TaxIdentifier;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private String name;

    private AddressDTO address;

    private String email;

    private String legalName;

    private List<TaxIdentifier> taxIdentifiers;

    private String website;

}
