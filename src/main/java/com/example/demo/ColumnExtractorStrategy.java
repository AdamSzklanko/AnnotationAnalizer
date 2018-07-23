package com.example.demo;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;

public class ColumnExtractorStrategy implements AnnotationMappingExtractorStrategy {
    @Override
    public Predicate<Field> isApplicable() {
        return field -> field.isAnnotationPresent(Column.class);
    }

    @Override
    public Map<String, String> extract(Field field) {
        String annotationName = field.getAnnotation(Column.class).name();
        String key = annotationName.isEmpty() ? field.getName() : annotationName;
        return Map.of(field.getName(), key);
    }
}
