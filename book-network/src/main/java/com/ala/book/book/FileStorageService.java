package com.ala.book.book;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile file, @NonNull Integer id) {
        final String fileUploadSubPath = fileUploadPath + separator + "users" +separator+ id;
        return uploadFile(file,fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile file, @NonNull String fileUploadSubPath) {
        File targetFile = new File(fileUploadSubPath);
        if (!targetFile.exists()){
            boolean isCreated = targetFile.mkdirs();
            if (!isCreated){
                log.warn("file was not created");
                return null;
            }
        }
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        final String finaleTargetPath = fileUploadSubPath + separator + System.currentTimeMillis() + "." + fileExtension;
        Path path = Paths.get(finaleTargetPath);
        try {
            Files.write(path,file.getBytes());
            return finaleTargetPath;
        }catch (IOException exception){
            log.warn("file was not uploaded");
        }
        return null;
    }


    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()){
            return "";
        }
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex == -1){
            return "";
        }
        return originalFilename.substring(lastDotIndex+1).toLowerCase();
    }
}



