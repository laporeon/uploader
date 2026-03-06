package com.laporeon.uploader.exceptions.custom;

public class InvalidFileExtensionException extends RuntimeException {

    private static final String INVALID_FILE_EXTENSION_MESSAGE = "Invalid or missing file extension";

    public InvalidFileExtensionException() {
        super(INVALID_FILE_EXTENSION_MESSAGE.formatted());
    }

}
