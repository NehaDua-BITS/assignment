package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.TaxIdentifierType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TaxIdentifier {

    private final TaxIdentifierType type;

    private String value;

    public TaxIdentifier(TaxIdentifierType type) {
        this(type, null);
    }

    public TaxIdentifier(@NotNull(message = "Tax identifier type cannot be null") TaxIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

}
