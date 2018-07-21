package com.example.demo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EmbeddedExtractorStrategy implements AnnotationMappingExtractorStrategy {
    @Override
    public Predicate<Field> isApplicable() {
        return field -> field.isAnnotationPresent(Embedded.class)
                && field.isAnnotationPresent(AttributeOverrides.class);
    }

    @Override
    public Map<String, String> extract(Field field) {
        Map<String, String> map = new HashMap<>();
        for (AttributeOverride attributeOverride : field.getAnnotation(AttributeOverrides.class).value()) {
            extractAttributeOverride(field, map, attributeOverride);
        }
        return map;
    }

    private void extractAttributeOverride(Field field, Map<String, String> map, AttributeOverride attributeOverride) {
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
