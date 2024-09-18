package com.ala.book.file;

import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
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
            log.warn(String.format("no file found in the path :: %s",fileUrl));
        }
        return null;

    }
}
