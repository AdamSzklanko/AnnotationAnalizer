package com.example.demo;

import javax.persistence.Embeddable;

@Embeddable
public class FullName {


    private String surname;
    private String lastname;
    private Size size;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
