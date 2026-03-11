package com.laporeon.uploader.controller;

import com.laporeon.uploader.dto.response.ErrorResponseDTO;
import com.laporeon.uploader.dto.response.FileUploadResponseDTO;
import com.laporeon.uploader.helpers.DocumentationExamples;
import com.laporeon.uploader.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Files", description = "Perform file related operations")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Operation(
            summary = "Upload a file",
            description = "Receives a file with valid extension. Returns upload information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request payload", required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "object", properties = {
                                    @StringToClassMapItem(key = "file", value = byte[].class)
                            })
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "File successfully uploaded",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FileUploadResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.UPLOAD_FILE_SUCCESS_RESPONSE))),
                    @ApiResponse(responseCode = "400", description = "Request validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.FILE_EXTENSION_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "413", description = "File upload limit exceeded",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.EXCEEDED_FILESIZE_LIMIT_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @PostMapping()
    public ResponseEntity<FileUploadResponseDTO> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        FileUploadResponseDTO fileUploadResponseDTO = fileStorageService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponseDTO);
    }

    @Operation(
            summary = "Download a file",
            description = "Receives a file name and download the file if exists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File successfully downloaded"),
                    @ApiResponse(responseCode = "400", description = "Request validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.VALIDATION_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.FILE_NOT_FOUND_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = fileStorageService.download(fileName);
        String contentType = fileStorageService.getContentType(fileName);

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);
    }

    @Operation(
            summary = "List files",
            description = "Retrieves a list of uploaded files.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Files successfully retrieved",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FileUploadResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.FILES_LIST_RESPONSE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> files = fileStorageService.listFiles();
        return ResponseEntity.ok().body(files);
    }

    @Operation(
            summary = "Delete a file",
            description = "Receives a file name and deletes the file if exists",
            responses = {
                    @ApiResponse(responseCode = "204", description = "File successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Request validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.VALIDATION_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.FILE_NOT_FOUND_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = DocumentationExamples.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<Void> delete(@PathVariable String fileName) throws IOException {
        fileStorageService.delete(fileName);
        return ResponseEntity.noContent().build();
    }

}
