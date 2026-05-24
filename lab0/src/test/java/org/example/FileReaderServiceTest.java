package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void testCreateFileReader_ShouldReturnNonNull() throws FileNotFoundException {
        Path testFile = tempDir.resolve("test.txt");

        assertDoesNotThrow(() -> {
        });
    }

    @Test
    void testReadFileContent_WithValidFile_ShouldReturnContent() throws IOException {
        Path testFile = tempDir.resolve("input.txt");
        String expectedContent = "Hello world\nThis is a test file.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile.toFile()))) {
            writer.write(expectedContent);
        }

        String actualContent = FileReaderService.readFileContent(testFile.toString());

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testReadFileContent_WithEmptyFile_ShouldReturnEmptyString() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        emptyFile.toFile().createNewFile();

        String content = FileReaderService.readFileContent(emptyFile.toString());

        assertEquals("", content);
    }

    @Test
    void testReadFileContent_WithNonExistentFile_ShouldThrowException() {
        String nonExistentFile = tempDir.resolve("does_not_exist.txt").toString();

        assertThrows(FileNotFoundException.class, () -> {
            FileReaderService.readFileContent(nonExistentFile);
        });
    }

    @Test
    void testCreateBufferedReader_ShouldReturnNonNull() throws FileNotFoundException {
        Path testFile = tempDir.resolve("test.txt");

        try {
            testFile.toFile().createNewFile();
            BufferedReader reader = FileReaderService.createBufferedReader(testFile.toString());
            assertNotNull(reader);
            reader.close();
        } catch (IOException e) {
            fail("Should not throw exception");
        }
    }
}