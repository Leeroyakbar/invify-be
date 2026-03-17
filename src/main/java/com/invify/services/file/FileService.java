package com.invify.services.file;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class FileService {
    private final Path root = Paths.get("uploads");

    public String saveFile(MultipartFile file, String subFolder, String slug) {
        try {
            String contentType = file.getContentType();
            String typePrefix = (contentType != null && contentType.startsWith("video")) ? "videos" : "images";

            Path resolvePath = root.resolve(subFolder);
            if (!Files.exists(resolvePath)) {
                Files.createDirectories(resolvePath);
            }

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = getFileName(file, slug) + "." + extension;

            Files.copy(file.getInputStream(), resolvePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            return "/" + typePrefix + "/" + subFolder + "/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
        }
    }

    public void deleteAllFilesBySlug(String subFolder, String slug) {
        try {
            Path resolvePath = root.resolve(subFolder);
            if (!Files.exists(resolvePath)) return;

            // Mencari file yang berawalan dengan "slug_"
            Files.walk(resolvePath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(slug + "_"))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Gagal menghapus file: " + path);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Gagal mengakses direktori untuk penghapusan: " + e.getMessage());
        }
    }

    protected String getFileName(MultipartFile file, String slug) {
        String originalName = Objects.requireNonNull(file.getOriginalFilename());
        String baseName = StringUtils.stripFilenameExtension(originalName); // "IMG_5087"

        String cleanName = baseName.replaceAll("[^a-zA-Z0-9]", "_");

        if (slug != null) {
            return slug + "_" + cleanName;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return timestamp + "_" + cleanName;
    }
}
