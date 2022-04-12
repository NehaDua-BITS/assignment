package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.Status;
import com.intuit.profilevalidationsystem.constants.TaxIdentifierType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "business_profile")
public class Profile {

    @Id
    protected final UUID profileId;

    protected String name;

    protected Address address;

    protected String email;

    private String legalName;

    private String website;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ElementCollection
    private List<TaxIdentifier> taxIdentifiers;

    @ElementCollection
    private List<String> subscriptionIds;

    public Profile(String name) {
        this(name, null ,null);
    }

    public Profile(String name, Address address, String email) {
        this.profileId = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.email = email;
        this.status = Status.ACTIVE;
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

