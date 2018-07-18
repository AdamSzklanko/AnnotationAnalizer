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
    @DisplayName("Positive")
    class Positive {
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
    }

    @Nested
    @DisplayName("Negative")
    class Negative {
    }

}