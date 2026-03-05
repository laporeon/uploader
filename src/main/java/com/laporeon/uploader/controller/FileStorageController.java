package com.laporeon.uploader.controller;

import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @GetMapping()
    public String uploadFile() {
        return "Hello, World!";
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileUploadResponseDTO> uploadFile(
            @RequestParam("file")MultipartFile file,
            @RequestParam("name") String name
    ) {
        FileUploadResponseDTO fileUploadResponseDTO = fileUploadService.upload(file, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponseDTO);
    }
}
