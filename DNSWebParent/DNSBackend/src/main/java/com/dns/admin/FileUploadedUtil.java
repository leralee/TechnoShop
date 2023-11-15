package com.dns.admin;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Component
public class FileUploadedUtil {
    public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Не удалось сохранить файл: " + filename, ex);
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);
        try (Stream<Path> files = Files.list(dirPath)) {
            files.forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Не удалось удалить файл: " + file);
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println("Не удалось вывести список каталогов: " + dirPath);
        }
    }

}
