package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Word Frequency Counter ===");
        System.out.println("Starting application...");

        if (args.length == 0) {
            System.out.println("No file specified. Please provide a file name as argument.");
            System.out.println("Example: java Main input.txt");
            return;
        }

        WordFrequencyCounter.main(args);

        System.out.println("Application finished");
    }
}