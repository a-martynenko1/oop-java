package org.example;

import java.io.*;
import java.util.*;

public class WordFrequencyCounter {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("File name not specified");
            System.err.println("Usage: java WordFrequencyCounter <filename>");
            return;
        }

        String fileName = args[0];

        try {
            String fileContent = FileReaderService.readFileContent(fileName);
            System.out.println("File successfully read");

            Map<String, Integer> frequencyMap = WordProcessor.countWordFrequency(fileContent);
            System.out.println("Found " + frequencyMap.size() + " unique words");
            System.out.println("File processing completed");

            CsvExporter.exportToCsv(frequencyMap, "output.csv");
            System.out.println("Results exported to output.csv");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getLocalizedMessage());
        }
    }
}