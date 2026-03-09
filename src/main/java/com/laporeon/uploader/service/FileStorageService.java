package com.laporeon.uploader.service;

import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.exceptions.custom.FileNotFoundException;
import com.laporeon.uploader.helpers.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private static final String DOWNLOAD_ENDPOINT = "/api/v1/files/download/";

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                                        .toAbsolutePath()
                                        .normalize();
    }

    public FileUploadResponseDTO upload(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = Validator.validateFileExtension(originalFileName);
        String fileName = UUID.randomUUID() + "." + fileExtension;

        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        file.transferTo(targetLocation);

        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path(DOWNLOAD_ENDPOINT + fileName)
                                                        .toUriString();

        return new FileUploadResponseDTO(
                fileName,
                file.getOriginalFilename(),
                downloadUrl,
                file.getContentType(),
                file.getSize(),
                Instant.now());
    }

    public Resource download(String fileName) throws MalformedURLException {
        Validator.validateFileName(fileName);
        return hasResource(fileName);
    }

    public List<String> listFiles() throws IOException {
        try (Stream<Path> files = Files.list(this.fileStorageLocation)) {
            return files.filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .filter(fileName -> !fileName.equals(".gitkeep"))
                        .toList();
        }
    }

    public void delete(String fileName) throws IOException {
        Validator.validateFileName(fileName);
        hasResource(fileName);
        Path filePath = resolvePath(fileName);
        Files.delete(filePath);
    }

    public String getContentType(String fileName) {
        String contentType = URLConnection.guessContentTypeFromName(fileName);

        if (contentType == null) {
            return "application/octet-stream";
        }

        return contentType;
    }

    private Resource hasResource(String fileName) throws MalformedURLException {
        Path filePath = resolvePath(fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException(fileName);
        }

        return resource;
    }

    private Path resolvePath(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }

}
