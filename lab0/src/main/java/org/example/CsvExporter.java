package org.example;

import java.util.*;
import java.io.*;
import java.util.Locale;

public class CsvExporter {

    public static void exportToCsv(Map<String, Integer> map, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            List<Map.Entry<String, Integer>> entryList = getSortedEntries(map);
            int totalWords = calculateTotalWords(entryList);

            writer.println("Word, Frequency, %");

            for (Map.Entry<String, Integer> entry : entryList) {
                String word = entry.getKey();
                int count = entry.getValue();
                double percent = (count * 100.0) / totalWords;
                writer.printf(Locale.US, "%s, %d, %.2f%%%n", word, count, percent);
            }
        }
    }

    private static List<Map.Entry<String, Integer>> getSortedEntries(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return entryList;
    }

    private static int calculateTotalWords(List<Map.Entry<String, Integer>> entries) {
        return entries.stream().mapToInt(Map.Entry::getValue).sum();
    }
}