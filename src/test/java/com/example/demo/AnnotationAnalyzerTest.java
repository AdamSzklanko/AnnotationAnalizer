package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

class AnnotationAnalyzerTest {

    @Test
    void parseNotEmpty() {

        //when
        Map<String, String> parse = AnnotationAnalyzer.parse(User.class);

        //then
        assertFalse(parse.isEmpty());
    }
}