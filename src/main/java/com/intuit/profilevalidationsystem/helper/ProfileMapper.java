package com.intuit.profilevalidationsystem.helper;

import com.intuit.profilevalidationsystem.dto.AddressDTO;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.model.Address;
import com.intuit.profilevalidationsystem.model.Profile;

public class ProfileMapper {

    public static Profile mapProfileDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile(profileDTO.getName(), mapAddressDTO(profileDTO.getAddress()), profileDTO.getEmail());
        profile.setLegalName(profileDTO.getLegalName());
        profile.setWebsite(profileDTO.getWebsite());
        profile.setEmail(profileDTO.getEmail());
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

    public static Profile mapDTOtoModel(ProfileDTO profileDTO, Profile profile) {
        if (profileDTO.getName() != null) {
            profile.setName(profileDTO.getName());
        }
        if (profileDTO.getEmail() != null) {
            profile.setEmail(profileDTO.getEmail());
        }
        if (profileDTO.getLegalName() != null) {
            profile.setLegalName(profileDTO.getLegalName());
        }

        if (profileDTO.getAddress() != null) {
            profile.setAddress(mapAddressDTO(profileDTO.getAddress()));
        }

        if (profileDTO.getWebsite() != null) {
            profile.setWebsite(profileDTO.getWebsite());
        }

        if(profileDTO.getTaxIdentifiers() != null) {
            profile.setTaxIdentifiers(profileDTO.getTaxIdentifiers());
        }
        return profile;
    }
}
