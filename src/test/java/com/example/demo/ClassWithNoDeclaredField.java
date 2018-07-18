package com.example.demo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;

public class ClassWithNoDeclaredField {

    @AttributeOverrides(value = {
            @AttributeOverride(name = "Type0", column = @Column(name = "SURNAME")),
    })
    @Embedded
    private FullName fullName;

}
