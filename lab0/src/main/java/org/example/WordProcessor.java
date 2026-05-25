package org.example;

import java.util.*;

public class WordProcessor {

    public static Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        StringBuilder currentWord = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                currentWord.append(ch);
            } else {
                addWordToMap(currentWord, frequencyMap);
            }
        }
        addWordToMap(currentWord, frequencyMap);

        return frequencyMap;
    }

    private static void addWordToMap(StringBuilder currentWord, Map<String, Integer> map) {
        if (currentWord.length() > 0) {
            String word = currentWord.toString().toLowerCase();
            map.merge(word, 1, Integer::sum);
            currentWord.setLength(0);
        }
    }
}