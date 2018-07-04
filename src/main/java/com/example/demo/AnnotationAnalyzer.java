package com.example.demo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.lang.reflect.Field;

public class AnnotationAnalyzer {

    public static void main(String[] args) throws NoSuchFieldException {

        for (Field field : User.class.getDeclaredFields()) {
            String name = field.getName();
            if (field.isAnnotationPresent(Column.class)) {
                String annotationName = field.getAnnotation(Column.class).name();
                if (annotationName.isEmpty()) {
                    System.out.println(name + " " + field.getName());
                } else {
                    System.out.println(name + " " + annotationName);
                }
            } else if (field.isAnnotationPresent(Embedded.class)) {
                for (AttributeOverride attributeOverride : field.getAnnotation(AttributeOverrides.class).value()) {
                    if (field.getType().getDeclaredField(attributeOverride.name()).getType().isEnum()) {
                        System.out.println(field.getName() + "." + attributeOverride.name() + ".id " + attributeOverride.column().name());
                    } else {
                        System.out.println(field.getName() + "." + attributeOverride.name() + " " + attributeOverride.column().name());
                    }
                }

            }


        }
    }

}
