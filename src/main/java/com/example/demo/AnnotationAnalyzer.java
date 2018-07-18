package com.example.demo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationAnalyzer {

    public static void main(String[] args) {
        parse(User.class);
    }

    /**
     * @param tClass class to be parsed
     * @return path to column name
     */
    public static Map<String, String> parse(Class tClass) {
        Map<String, String> map = new HashMap<String, String>();
        for (Field field : tClass.getDeclaredFields()) {
            String name = field.getName();
            if (field.isAnnotationPresent(Column.class)) {
                String annotationName = field.getAnnotation(Column.class).name();
                if (annotationName.isEmpty()) {
                    map.put(name, field.getName());
                } else {
                    map.put(name, annotationName);
                }
            } else if (field.isAnnotationPresent(Embedded.class)) {
                for (AttributeOverride attributeOverride : field.getAnnotation(AttributeOverrides.class).value()) {
                    try {
                        if (field.getType().getDeclaredField(attributeOverride.name()).getType().isEnum()) {
                            map.put(field.getName() + "." + attributeOverride.name() + ".id", attributeOverride.column().name());
                        } else {
                            map.put(field.getName() + "." + attributeOverride.name(), attributeOverride.column().name());
                        }
                    } catch (NoSuchFieldException e) {
                        //friendly exception skip this field
                    }
                }

            }
        }
        return map;
    }
}
