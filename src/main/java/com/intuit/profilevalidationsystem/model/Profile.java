package com.intuit.profilevalidationsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Setter
public class Profile {

    protected final UUID id;

    protected String name;

    protected Address address;

    protected String email;

    public Profile(String name) {
        this(name, null, null);
    }

    public Profile(String name, Address address, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.email = email;
    }

}
