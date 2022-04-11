package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.TaxIdentifierType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class BusinessProfile extends Profile {

    private String legalName;

    private List<TaxIdentifier> taxIdentifiers;

    private String website;

    private List<String> subscriptionIds;

    public BusinessProfile(String name) {
        super(name);
    }

    public BusinessProfile(String name, Address address, String email) {
        super(name, address, email);
    }

    /**
     * Adds the tax identifier if does not exist
     * else updates the current value
     * @param type
     * @param value
     */
    public void addTaxIdentifier(TaxIdentifierType type, String value) {
        Optional<TaxIdentifier> taxIdentifierOptional = taxIdentifiers.stream().filter(t -> t.getType() == type).findFirst();
        if (!taxIdentifierOptional.isPresent()) { //add new identifier
            this.taxIdentifiers.add(new TaxIdentifier(type, value));
        } else {
            taxIdentifierOptional.get().setValue(value); //update value of existing identifier
        }
    }

}

