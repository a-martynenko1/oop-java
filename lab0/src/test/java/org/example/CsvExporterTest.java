package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void testExportToCsv_ShouldCreateFile() throws IOException {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("hello", 5);
        testMap.put("world", 3);
        testMap.put("java", 2);

        String outputFile = tempDir.resolve("test_output.csv").toString();

        assertDoesNotThrow(() -> {
            CsvExporter.exportToCsv(testMap, outputFile);
        });

        File file = new File(outputFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testExportToCsv_ContentFormat_ShouldBeCorrect() throws IOException {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("hello", 5);
        testMap.put("world", 3);

        String outputFile = tempDir.resolve("format_test.csv").toString();
        CsvExporter.exportToCsv(testMap, outputFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String header = reader.readLine();
            assertEquals("Word, Frequency, %", header);

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            assertEquals(2, lines.size());

            for (String csvLine : lines) {
                String[] parts = csvLine.split(", ");
                assertEquals(3, parts.length);

                if (parts[0].equals("hello")) {
                    assertEquals("5", parts[1]);
                    assertTrue(parts[2].startsWith("62.50"));
                } else if (parts[0].equals("world")) {
                    assertEquals("3", parts[1]);
                    assertTrue(parts[2].startsWith("37.50"));
                }
            }
        }
    }

    @Test
    void testExportToCsv_SortedByFrequency_ShouldBeDescending() throws IOException {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("rare", 1);
        testMap.put("medium", 5);
        testMap.put("frequent", 10);

        String outputFile = tempDir.resolve("sorted_test.csv").toString();
        CsvExporter.exportToCsv(testMap, outputFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            reader.readLine();

            String firstLine = reader.readLine();
            String secondLine = reader.readLine();
            String thirdLine = reader.readLine();

            assertTrue(firstLine.contains("frequent"));
            assertTrue(secondLine.contains("medium"));
            assertTrue(thirdLine.contains("rare"));
        }
    }

    @Test
    void testExportToCsv_WithEmptyMap_ShouldCreateFileWithOnlyHeader() throws IOException {
        Map<String, Integer> emptyMap = new HashMap<>();
        String outputFile = tempDir.resolve("empty.csv").toString();

        CsvExporter.exportToCsv(emptyMap, outputFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String header = reader.readLine();
            assertEquals("Word, Frequency, %", header);
            assertNull(reader.readLine());
        }
    }

    @Test
    void testExportToCsv_WithSingleEntry_ShouldCalculateCorrectPercentage() throws IOException {
        Map<String, Integer> singleMap = new HashMap<>();
        singleMap.put("only", 100);

        String outputFile = tempDir.resolve("single.csv").toString();
        CsvExporter.exportToCsv(singleMap, outputFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            reader.readLine();
            String line = reader.readLine();
            assertTrue(line.contains("only, 100, 100.00%"));
        }
    }

    @Test
    void testExportToCsv_PercentageSum_ShouldBeApproximately100() throws IOException {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("word1", 3);
        testMap.put("word2", 3);
        testMap.put("word3", 3);

        String outputFile = tempDir.resolve("percent_sum.csv").toString();
        CsvExporter.exportToCsv(testMap, outputFile);

        double totalPercentage = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String percentStr = parts[2].replace("%", "");
                totalPercentage += Double.parseDouble(percentStr);
            }
        }

        assertTrue(Math.abs(totalPercentage - 100.0) < 0.01);
    }
}