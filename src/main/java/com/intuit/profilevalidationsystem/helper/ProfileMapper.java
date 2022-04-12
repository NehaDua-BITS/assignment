package com.intuit.profilevalidationsystem.helper;

import com.intuit.profilevalidationsystem.dto.AddressDTO;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.model.Address;
import com.intuit.profilevalidationsystem.model.Profile;
import com.intuit.profilevalidationsystem.model.TaxIdentifier;

import java.util.ArrayList;
import java.util.List;

public class ProfileMapper {

    public static Profile mapProfileDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile(profileDTO.getName(), mapAddressDTO(profileDTO.getAddress()), profileDTO.getEmail());
        profile.setLegalName(profileDTO.getLegalName());
        profile.setWebsite(profileDTO.getWebsite());
        profile.setEmail(profileDTO.getEmail());
        List<TaxIdentifier> identifierList = new ArrayList<>();
        profile.setTaxIdentifiers(profileDTO.getTaxIdentifiers());
        return profile;
    }

    public static Address mapAddressDTO(AddressDTO addressDTO) {
        Address address = Address.builder()
                .line1(addressDTO.getLine1())
                .line2(addressDTO.getLine2())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .country(addressDTO.getCountry())
                .zipCode(addressDTO.getZipCode())
                .build();

        return address;
    }
}
