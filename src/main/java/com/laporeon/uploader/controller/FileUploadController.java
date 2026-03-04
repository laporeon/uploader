package com.laporeon.uploader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    @GetMapping()
    public String uploadFile() {
        return "Hello, World!";
    }
}
