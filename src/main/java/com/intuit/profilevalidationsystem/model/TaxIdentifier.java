package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.TaxIdentifierType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class TaxIdentifier {

    @Enumerated(EnumType.STRING)
    private TaxIdentifierType type;

    private String value;


    public TaxIdentifier(TaxIdentifierType type) {
        this(type, null);
    }

    public TaxIdentifier(@NotNull(message = "Tax identifier type cannot be null") TaxIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

}
