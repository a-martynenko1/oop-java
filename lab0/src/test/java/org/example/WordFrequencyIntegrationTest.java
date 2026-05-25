package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void testCompleteWorkflow() throws IOException {
        Path testFile = tempDir.resolve("test_input.txt");
        String testContent = "Java is great. Java is powerful. Java is popular.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile.toFile()))) {
            writer.write(testContent);
        }

        String content = FileReaderService.readFileContent(testFile.toString());

        Map<String, Integer> frequencyMap = WordProcessor.countWordFrequency(content);

        assertEquals(5, frequencyMap.size());
        assertEquals(3, frequencyMap.get("java"));
        assertEquals(3, frequencyMap.get("is"));
        assertEquals(1, frequencyMap.get("great"));
        assertEquals(1, frequencyMap.get("powerful"));
        assertEquals(1, frequencyMap.get("popular"));

        String outputFile = tempDir.resolve("output.csv").toString();
        CsvExporter.exportToCsv(frequencyMap, outputFile);

        File csvFile = new File(outputFile);
        assertTrue(csvFile.exists());
        assertTrue(csvFile.length() > 0);
    }
}