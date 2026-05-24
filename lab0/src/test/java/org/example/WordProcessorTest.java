package org.example;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WordProcessorTest {

    @Test
    void testCountWordFrequency_WithSimpleText_ShouldReturnCorrectCounts() {
        String text = "hello world hello java world world";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(3, result.size());
        assertEquals(2, result.get("hello"));
        assertEquals(3, result.get("world"));
        assertEquals(1, result.get("java"));
    }

    @Test
    void testCountWordFrequency_WithMixedCase_ShouldBeCaseInsensitive() {
        String text = "Hello HELLO hello HeLlO WORLD World";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(2, result.size());
        assertEquals(4, result.get("hello"));
        assertEquals(2, result.get("world"));
    }

    @Test
    void testCountWordFrequency_WithPunctuation_ShouldIgnorePunctuation() {
        String text = "Hello, world! Hello... world??? This is a test.";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(6, result.size());
        assertEquals(2, result.get("hello"));
        assertEquals(2, result.get("world"));
        assertEquals(1, result.get("this"));
        assertEquals(1, result.get("is"));
        assertEquals(1, result.get("a"));
        assertEquals(1, result.get("test"));
    }

    @Test
    void testCountWordFrequency_WithNumbers_ShouldIncludeNumbers() {
        String text = "Java 8 Java 11 Java 17";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(4, result.size());
        assertEquals(3, result.get("java"));
        assertEquals(1, result.get("8"));
        assertEquals(1, result.get("11"));
        assertEquals(1, result.get("17"));
    }

    @Test
    void testCountWordFrequency_WithEmptyString_ShouldReturnEmptyMap() {
        String text = "";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCountWordFrequency_WithOnlyPunctuation_ShouldReturnEmptyMap() {
        String text = "!!! ,,, ... ;;; ???";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCountWordFrequency_WithNewLinesAndTabs_ShouldHandleCorrectly() {
        String text = "Hello\nworld\nHello\tworld\nHello";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(2, result.size());
        assertEquals(3, result.get("hello"));
        assertEquals(2, result.get("world"));
    }

    @Test
    void testCountWordFrequency_WithSingleCharacter_ShouldCountCorrectly() {
        String text = "a a a b b c";

        Map<String, Integer> result = WordProcessor.countWordFrequency(text);

        assertEquals(3, result.size());
        assertEquals(3, result.get("a"));
        assertEquals(2, result.get("b"));
        assertEquals(1, result.get("c"));
    }
}