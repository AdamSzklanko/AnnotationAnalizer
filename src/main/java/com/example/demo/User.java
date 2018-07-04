package com.example.demo;


import javax.persistence.*;

@Entity
public class User {

    @Column(name = "USER_NAME")
    private String name;

    @Column
    private String noname;

    @AttributeOverrides(value = {
            @AttributeOverride(name = "surname", column = @Column(name = "SURNAME")),
            @AttributeOverride(name = "lastname", column = @Column(name = "LASTNAME")),
            @AttributeOverride(name = "size", column = @Column(name = "SIZE"))
    })
    @Embedded
    private FullName fullName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
