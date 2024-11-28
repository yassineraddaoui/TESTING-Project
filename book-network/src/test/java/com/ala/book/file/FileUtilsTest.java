package com.ala.book.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileUtilsTest {


    @Test
    void testReadFileFromLocationWithValidPath()  {
        String validPath = "test-file.txt";
        byte[] expectedContent = "Test content".getBytes();

        // Mocking File and Files
        File mockFile = mock(File.class);
        when(mockFile.toPath()).thenReturn(new File(validPath).toPath());

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(any())).thenReturn(expectedContent);

            byte[] actualContent = FileUtils.readFileFromLocation(validPath);

            assertNotNull(actualContent, "Content should not be null for a valid path.");
            assertArrayEquals(expectedContent, actualContent, "File content should match expected content.");
        }
    }

    @Test
    void testReadFileFromLocationWithInvalidPath() {
        String invalidPath = "non-existent-file.txt";

        byte[] result = FileUtils.readFileFromLocation(invalidPath);

        assertNull(result, "Result should be null for an invalid path.");
    }

    @Test
    void testReadFileFromLocationWithBlankPath() {
        String blankPath = "   ";

        byte[] result = FileUtils.readFileFromLocation(blankPath);

        assertNull(result, "Result should be null for a blank path.");
    }

    @Test
    void testReadFileFromLocationThrowsIOException() throws IOException {
        String validPath = "test-file.txt";

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(any())).thenThrow(new IOException("File error"));

            byte[] result = FileUtils.readFileFromLocation(validPath);

            assertNull(result, "Result should be null when an IOException occurs.");
        }
    }
}
