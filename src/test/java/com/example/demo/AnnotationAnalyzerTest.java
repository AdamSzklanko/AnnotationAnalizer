package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Annotation Analyzer Test")
class AnnotationAnalyzerTest {

    @Nested
    @DisplayName("Positive Java6")
    class PositiveJava6 {
        @Test
        @DisplayName("Empty mapping on any class")
        void parseAnyClass() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parse(String.class);

            //then
            assertTrue(parse.isEmpty());
        }

        @Test
        @DisplayName("Maps simple")
        void parseUser() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parse(User.class);

            //then
            assertFalse(parse.isEmpty());
        }

        @Test
        @DisplayName("Skips undeclared fields gracefully")
        void parseCanHandleUndeclaredFields() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parse(ClassWithNoDeclaredField.class);

            //then
            assertTrue(parse.isEmpty());
        }
    }

    @Nested
    @DisplayName("PositiveJava8")
    class PositiveJava8 {
        @Test
        @DisplayName("Empty mapping on any class")
        void parseAnyClass() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parser(String.class);

            //then
            assertTrue(parse.isEmpty());
        }

        @Test
        @DisplayName("Maps simple")
        void parseUser() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parser(User.class);

            //then
            assertFalse(parse.isEmpty());
        }

        @Test
        @DisplayName("Skips undeclared fields gracefully")
        void parseCanHandleUndeclaredFields() {

            //when
            Map<String, String> parse = AnnotationAnalyzer.parser(ClassWithNoDeclaredField.class);

            //then
            assertTrue(parse.isEmpty());
        }
    }

    @Nested
    @DisplayName("Negative")
    class Negative {
    }

}