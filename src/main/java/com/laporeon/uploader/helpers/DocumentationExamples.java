package com.laporeon.uploader.helpers;

public class DocumentationExamples {

    public static final String UPLOAD_FILE_SUCCESS_RESPONSE = """
            {
                "fileName": "ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
                "originalFileName": "document.pdf",
                "downloadUrl": "http://localhost:8080/api/v1/files/download/ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
                "fileType": "application/pdf",
                "fileSize": 242401,
                "uploadedAt": "2026-03-06T17:30:25.000Z"
            }
            """;

    public static final String FILES_LIST_RESPONSE = """
            [
                "ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
                "a1b2c3d4-1234-5678-abcd-ef1234567890.png"
            ]
            """;

    public static final String FILE_NOT_FOUND_ERROR_MESSAGE = """
            {
                "timestamp": "2025-12-02T16:20:26.685371366Z",
                "status": 404,
                "type": "NOT_FOUND_ERROR",
                "message": "File '123e4567-e89b-12d3-a456-426614174000.pdf' not found"
            }
            """;

    public static final String FILE_EXTENSION_ERROR_MESSAGE = """
            {
                "timestamp": "2025-12-02T16:20:26.685371366Z",
                "status": 400,
                "type": "VALIDATION_ERROR",
                "message": "Invalid or missing file extension"
            }
            """;

    public static final String VALIDATION_ERROR_MESSAGE = """
            {
                "timestamp": "2025-12-02T16:20:26.685371366Z",
                "status": 400,
                "type": "VALIDATION_ERROR",
                "message": "Invalid file name: 'invalid.name'. Expected format: UUID followed by an allowed extension (e.g., '123e4567-e89b-12d3-a456-426614174000.pdf')."
            }
            """;

    public static final String EXCEEDED_FILESIZE_LIMIT_ERROR_MESSAGE = """
            {
                "timestamp": "2025-12-02T16:20:26.685371366Z",
                "status": 400,
                "type": "MAX_UPLOAD_SIZE_EXCEEDED_ERROR",
                "message": "File size exceeded the upload limit"
            }
            """;

    public static final String INTERNAL_ERROR_MESSAGE = """
            {
                "timestamp": "2025-10-29T15:19:52.121160501Z",
                "status": 500,
                "type": "INTERNAL_SERVER_ERROR",
                "message": "An unexpected error occurred"
            }
            """;
}
