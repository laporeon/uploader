package com.laporeon.uploader.exceptions.custom;

public class FileNotFoundException extends RuntimeException {

    private static final String FILE_NOT_FOUND_MESSAGE = "File '%s' not found";

    public FileNotFoundException(String fileName) {
        super(FILE_NOT_FOUND_MESSAGE.formatted(fileName));
    }

}
