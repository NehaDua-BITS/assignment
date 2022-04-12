package com.intuit.profilevalidationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.intuit.profilevalidationsystem.constants.Status;
import com.intuit.profilevalidationsystem.constants.TaxIdentifierType;
import com.intuit.profilevalidationsystem.exceptions.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "business_profile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    @Id
    @Column(name = "profile_id")
    @Type(type = "uuid-char")
    protected final UUID profileId;

    protected String name;

    protected String address;

    protected String email;

    @Column(name = "legal_name" )
    private String legalName;

    private String website;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "tax_identifiers")
    private String taxIdentifiers;

    @Column(name = "subscription_ids")
    private String subscriptionIds;

    @Transient
    @JsonIgnore
    private List<String> subscriptionIdsList;

    @Transient
    @JsonIgnore
    private List<TaxIdentifier> taxIdentifiersList;

    public Profile() {
        this(null);
    }

    public Profile(String name) {
        this(name, null ,null);
    }

    public Profile(String name, Address address, String email) {
        this.profileId = UUID.randomUUID();
        this.name = name;
        this.address = address != null ? address.toString() : null;
        this.email = email;
        this.status = Status.ACTIVE;
        this.taxIdentifiers = "NA";
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new InvalidInputException(ErrorCodes.ADDRESS_NULL);
        }
        this.address = address.toString();
    }

    public void setTaxIdentifiers(List<TaxIdentifier> taxIdentifiersList) {
        this.taxIdentifiersList = taxIdentifiersList;
        if (taxIdentifiersList != null) {
            taxIdentifiers = Arrays.toString(taxIdentifiersList.toArray());
        }
    }

    public void setSubscriptionIdsList(List<String> subscriptionIdsList) {
        this.subscriptionIdsList = subscriptionIdsList;
        if (subscriptionIdsList != null) {
            this.subscriptionIds = Arrays.toString(subscriptionIdsList.toArray());
        }
    }
}

