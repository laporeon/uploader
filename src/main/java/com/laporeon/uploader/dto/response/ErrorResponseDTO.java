package com.laporeon.uploader.dto.response;

import java.time.Instant;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String type,
        String message
) {
}
