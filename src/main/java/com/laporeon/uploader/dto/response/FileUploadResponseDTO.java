package com.laporeon.uploader.dto.response;

import java.time.Instant;

public record FileUploadResponseDTO(
        String fileName,
        String originalFileName,
        String downloadUrl,
        String fileType,
        long fileSize,
        Instant uploadedAt
) {
}
