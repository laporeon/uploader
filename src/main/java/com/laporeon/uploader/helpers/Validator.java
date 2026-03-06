package com.laporeon.uploader.helpers;

import com.laporeon.uploader.exceptions.custom.InvalidFileExtensionException;

import java.util.List;

public class Validator {

    private static final List<String> ALLOWED_FILE_EXTENSIONS = List.of(
            "jpg", "jpeg", "png", "svg", "gif", "webp", "bmp", "ico",
            "pdf", "txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "csv",
            "mp3", "wav", "ogg", "flac", "mp4", "avi", "mov", "mkv", "webm"
    );

    public static String validateFileExtension(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf('.');
        String fileExtension = originalFileName.substring(fileExtensionIndex + 1).toLowerCase();

        if (fileExtensionIndex == -1 || !ALLOWED_FILE_EXTENSIONS.contains(fileExtension)) {
            throw new InvalidFileExtensionException();
        }

        return fileExtension;
    }
}
