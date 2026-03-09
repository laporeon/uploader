package com.laporeon.uploader.helpers;

import com.laporeon.uploader.exceptions.custom.InvalidFileNameException;

import java.util.List;

public class Validator {

    private static final List<String> ALLOWED_FILE_EXTENSIONS = List.of(
            "jpg", "jpeg", "png", "svg", "gif", "webp", "bmp", "ico",
            "pdf", "txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "csv",
            "mp3", "wav", "ogg", "flac", "mp4", "avi", "mov", "mkv", "webm"
    );

    private static final String FILENAME_REGEX_VALIDATOR =
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\." +
                    "(jpg|jpeg|png|svg|gif|webp|bmp|ico|" +
                    "pdf|txt|doc|docx|xls|xlsx|ppt|pptx|csv|" +
                    "mp3|wav|ogg|flac|mp4|avi|mov|mkv|webm)$";

    public static String validateFileExtension(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf('.');
        String fileExtension = originalFileName.substring(fileExtensionIndex + 1).toLowerCase();

        if (fileExtensionIndex == -1 || !ALLOWED_FILE_EXTENSIONS.contains(fileExtension)) {
            throw new InvalidFileNameException("Invalid or missing file extension");
        }

        return fileExtension;
    }

    public static void validateFileName(String fileName) {
        String invalidFileNameMessage = "Invalid file name: '%s'. Expected format: UUID followed by an allowed extension " +
                "(e.g., '123e4567-e89b-12d3-a456-426614174000.pdf').";

        if (!fileName.matches(FILENAME_REGEX_VALIDATOR)) {
            throw new InvalidFileNameException(invalidFileNameMessage.formatted(fileName));
        }
    }
}
