package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    static Stream<Arguments> correctMappingForUser() {
        return Stream.of(
                Arguments.of("name", "USER_NAME"),
                Arguments.of("fullName.surname", "SURNAME"),
                Arguments.of("fullName.lastname", "LASTNAME"),
                Arguments.of("fullName.size.id", "SIZE"),
                Arguments.of("noname", "noname")
        );
    }

    @Nested
    @DisplayName("Negative")
    class Negative {
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

        @ParameterizedTest
        @MethodSource("com.example.demo.AnnotationAnalyzerTest#correctMappingForUser")
        void testWithMultiArgMethodSource(String key, String value) {
            //when
            Map<String, String> parse = AnnotationAnalyzer.parse(User.class);

            //then
            assertTrue(
                    parse.containsKey(key),
                    () -> String.format("The map doesn't contain the key: %s", key)
            );
            assertEquals(
                    parse.get(key), value,
                    () -> String.format("Should key: %s equal value: %s", key, value)
            );
        }


    }
}