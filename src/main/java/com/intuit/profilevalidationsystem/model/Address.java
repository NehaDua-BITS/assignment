package com.intuit.profilevalidationsystem.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Address {

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String zipCode;

    private String country;

}
