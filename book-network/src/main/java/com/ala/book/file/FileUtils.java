package com.ala.book.file;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)){
            return null;
        }
        try {
//            Path filePath = Paths.get(fileUrl);
            Path filePath = new File(fileUrl).toPath();
            return Files.readAllBytes(filePath);

        }catch (IOException exception){
            System.out.println("Error");
        }
        return null;

    }
}
