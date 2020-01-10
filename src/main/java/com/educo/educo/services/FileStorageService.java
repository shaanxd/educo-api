package com.educo.educo.services;

import com.educo.educo.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;


    public FileStorageService() {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/uploads")
                .toAbsolutePath()
                .normalize();
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(this.fileStorageLocation);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        // Generate new UUID
        String uuid = UUID.randomUUID().toString();

        // Get image format
        String[] split = filename.split("\\.");

        // Concat image format to UUID to make unique filename
        String uuidWithExt = uuid.concat("." + split[split.length - 1]);

        try {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(uuidWithExt), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new GenericException("Error occurred while uploading images. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidWithExt;
    }

    List<String> storeMultiple(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file.getContentType() == null || (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png"))) {
                throw new GenericException("Unsupported file type. Please try again with valid images.", HttpStatus.BAD_REQUEST);
            }
        }

        List<String> filenames = new ArrayList<>();

        for (MultipartFile file : files) {
            filenames.add(store(file));
        }

        return filenames;
    }
}
