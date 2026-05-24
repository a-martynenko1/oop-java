package org.example;

import java.io.*;

public class FileReaderService {

    public static FileReader createFileReader(String fileName) throws FileNotFoundException {
        return new FileReader(fileName);
    }

    public static BufferedReader createBufferedReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    public static String readFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int charCode;
            while ((charCode = reader.read()) != -1) {
                content.append((char) charCode);
            }
        }
        return content.toString();
    }
}