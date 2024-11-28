package com.ala.book.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class FileStorageServiceTest {

    @InjectMocks
    private FileStorageService fileStorageService;

    @Mock(lenient = true)
    private  MultipartFile multipartFile;

    private final String fileUploadPath = "/tmp/uploads";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fileStorageService, "fileUploadPath", fileUploadPath);
    }

    @Test
    void saveFile_ShouldSaveFileSuccessfully() throws IOException {
        // Arrange
        Integer userId = 123;
        String originalFilename = "example.png";
        byte[] fileContent = "dummy content".getBytes();
        when(multipartFile.getOriginalFilename()).thenReturn(originalFilename);
        when(multipartFile.getBytes()).thenReturn(fileContent);

        // Act
        String filePath = fileStorageService.saveFile(multipartFile, userId);

        // Assert
        assertNotNull(filePath);
        assertTrue(filePath.startsWith(fileUploadPath + File.separator + "users" + File.separator + userId));
        assertTrue(new File(filePath).exists());

        // Clean up
        new File(filePath).delete();
    }

    @Test
    void saveFile_ShouldReturnNullWhenDirectoryCreationFails() {
        // Arrange
        Integer userId = 123;
        String invalidPath = "/invalid-path";
        ReflectionTestUtils.setField(fileStorageService, "fileUploadPath", invalidPath);
        when(multipartFile.getOriginalFilename()).thenReturn("example.png");

        // Act
        String filePath = fileStorageService.saveFile(multipartFile, userId);

        // Assert
        assertNull(filePath);
    }



    @Test
    void saveFile_ShouldHandleIOExceptionDuringFileWrite() throws IOException {
        // Arrange
        Integer userId = 123;
        String originalFilename = "example.png";
        when(multipartFile.getOriginalFilename()).thenReturn(originalFilename);
        when(multipartFile.getBytes()).thenThrow(new IOException("Simulated IOException"));

        // Act
        String filePath = fileStorageService.saveFile(multipartFile, userId);

        // Assert
        assertNull(filePath);
    }

    @Test
    void getFileExtension_ShouldReturnCorrectExtension() {
        // Arrange
        String filename = "example.png";

        // Act
        String extension = ReflectionTestUtils.invokeMethod(fileStorageService, "getFileExtension", filename);

        // Assert
        assertEquals("png", extension);
    }

    @Test
    void getFileExtension_ShouldReturnEmptyForNoExtension() {
        // Arrange
        String filename = "example";

        // Act
        String extension = ReflectionTestUtils.invokeMethod(fileStorageService, "getFileExtension", filename);

        // Assert
        assertEquals("", extension);
    }

    @Test
    void getFileExtension_ShouldReturnEmptyForNullFilename() {
        // Act
        String extension = ReflectionTestUtils.invokeMethod(fileStorageService, "getFileExtension", (String) null);

        // Assert
        assertEquals("", extension);
    }
}
