package com.laporeon.uploader.service;

import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.helpers.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

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
        String fileName = UUID.randomUUID() + "."  + fileExtension;

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
}
