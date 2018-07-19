package com.example.demo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class AnnotationAnalyzer {

    private AnnotationAnalyzer() {
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
                    map.put(field.getName(), field.getName());
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

    /**
     * @param tClass class to be parsed
     * @return path to column name
     */
    public static Map<String, String> parser(Class tClass) {
        Map<String, String> collectColumns = Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(AnnotationAnalyzer::parseColumn)
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        Map<String, String> collectEmbedded = Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Embedded.class))
                .filter(field -> field.isAnnotationPresent(AttributeOverrides.class))
                .flatMap(AnnotationAnalyzer::parseEmbedded)
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        return mergeMaps(collectColumns, collectEmbedded);
    }

    public static AbstractMap.SimpleEntry<String, String> parseColumn(Field field) {
        String annotationName = field.getAnnotation(Column.class).name();
        String key = annotationName.isEmpty() ? field.getName() : annotationName;
        return new AbstractMap.SimpleEntry<>(key, field.getName());
    }

    public static AbstractMap.SimpleEntry<String, String> parseAttributeOverride(Field field, AttributeOverride attributeOverride) {
        try {
            if (field.getType().getDeclaredField(attributeOverride.name()).getType().isEnum()) {
                return new AbstractMap.SimpleEntry<>(field.getName() + "." + attributeOverride.name() + ".id", attributeOverride.column().name());
            } else {
                return new AbstractMap.SimpleEntry<>(field.getName() + "." + attributeOverride.name(), attributeOverride.column().name());
            }
        } catch (NoSuchFieldException e) {
            //friendly exception skip this field
        }
        return new AbstractMap.SimpleEntry<>(null, null);
    }

    public static Stream<AbstractMap.SimpleEntry<String, String>> parseEmbedded(Field field) {
        return Arrays.stream(field.getAnnotation(AttributeOverrides.class).value())
                .filter(attributeOverride -> doesObjectContainField(field, attributeOverride.name()))
                .map(attributeOverride -> parseAttributeOverride(field, attributeOverride));
    }

    public static Map<String, String> mergeMaps(Map<String, String> a,
                                                Map<String, String> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (s, s2) -> s));
    }

    public static boolean doesObjectContainField(Field field, String fieldName) {
        return Arrays.stream(field.getType().getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }
}
