package com.laporeon.uploader.controller;

import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @PostMapping()
    public ResponseEntity<FileUploadResponseDTO> uploadFile(
            @RequestParam("file")MultipartFile file
    ) throws IOException {
        FileUploadResponseDTO fileUploadResponseDTO = fileStorageService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponseDTO);
    }
}
