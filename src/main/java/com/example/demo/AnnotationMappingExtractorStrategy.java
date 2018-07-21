package com.example.demo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;

public interface AnnotationMappingExtractorStrategy {

    Predicate<Field> isApplicable();

    Map<String, String> extract(Field field);
}
