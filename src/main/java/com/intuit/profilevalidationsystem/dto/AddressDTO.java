package com.intuit.profilevalidationsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String zipCode;

    private String country;

    public AddressDTO() {
    }

    public AddressDTO(String line1, String line2, String city, String state, String zipCode, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

}
