package com.laporeon.uploader.controller;

import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = fileStorageService.download(fileName);
        String contentType = fileStorageService.getContentType(fileName);

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);
    }

    @GetMapping
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> files = fileStorageService.listFiles();
        return ResponseEntity.ok().body(files);
    }

}
