<h1 align="center">Uploader API

![java](https://img.shields.io/static/v1?label=java&message=21.0.10&labelColor=2d3748&color=grey&logo=openjdk&logoColor=white&style=flat)
![spring boot](https://img.shields.io/static/v1?label=spring%20boot&message=4.0.3&color=2d3748&logo=springboot&style=flat-square)
![maven](https://img.shields.io/static/v1?label=maven&message=3.9.12&labelColor=2d3748&color=grey&logo=apachemaven&logoColor=white&style=flat)
[![MIT License](https://img.shields.io/badge/license-MIT-green?style=flat-square)](https://github.com/laporeon/uploader/blob/main/LICENSE.md)

</h1>

## Table of Contents

- [About](#about)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
    - [Configuring](#configuring)
        - [application.properties](#applicationproperties)
        - [.env](#env)
- [Usage](#usage)
    - [Starting](#starting)
    - [Routes](#routes)
        - [Requests](#requests)

## About

A simple REST API built for learning how to handle file uploads and downloads with Spring Boot.

**Key features:**

- Uploads files with automatic UUID-based naming to avoid collisions.
- Validates file extensions against a predefined allowlist.
- Enforces a configurable maximum file size (default: 10MB).
- Download files with correct content type detection.
- List all available uploaded files.

## Requirements

- Java 21+
- Maven 3.9+

## Getting Started

### Configuring

#### **application.properties**

| Property                                       | Default | Description                                  |
|------------------------------------------------|---------|----------------------------------------------|
| `server.port`                                  | `8080`  | Server port                                  |
| `file.upload-dir`                              | `tmp`   | Directory to store files                     |
| `spring.servlet.multipart.file-size-threshold` | `2KB`   | File size threshold before writing to disk   |
| `spring.servlet.multipart.max-file-size`       | `10MB`  | Maximum size of a single uploaded file       |
| `spring.servlet.multipart.max-request-size`    | `10MB`  | Maximum size of the entire multipart request |

> [!NOTE]
> If you change the default value for `file.upload-dir`, you must manually create the folder, since the API does not
> handle directory creation.

#### **.env**

Configuring the `.env` file is optional since most values can be defined directly in [application.properties](./src/main/resources/application.properties).

Rename the `.env.example` file to `.env` and modify the variables according to your needs.

| Variable        | Default   | Description              |
|-----------------|-----------|--------------------------|
| PORT            | `8080`    | Server port              |

## Usage

### Starting

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080` (or the port you configured).

### Routes

| Route                               | HTTP Method | Params                                | Description                 | Auth Method |
|-------------------------------------|-------------|---------------------------------------|-----------------------------|-------------|
| `/api/v1/files`                     | POST        | **Body** with `file` (multipart/form-data) | Upload a file               | None        |
| `/api/v1/files/download/{fileName}` | GET         | **Path:** `fileName`        | Download a file by its name | None        |
| `/api/v1/files`                     | GET         | —                                     | List all uploaded files     | None        |

#### Requests

- `POST /api/v1/files`

**Allowed Extensions**

| Category  | Extensions                                                       |
|-----------|------------------------------------------------------------------|
| Images    | `jpg`, `jpeg`, `png`, `svg`, `gif`, `webp`, `bmp`, `ico`         |
| Documents | `pdf`, `txt`, `doc`, `docx`, `xls`, `xlsx`, `ppt`, `pptx`, `csv` |
| Audio     | `mp3`, `wav`, `ogg`, `flac`                                      |
| Video     | `mp4`, `avi`, `mov`, `mkv`, `webm`                               |

Request body (`multipart/form-data`):

```
file: <binary file>
```
Example:

```bash
curl -X POST http://localhost:8080/api/v1/files -F "file=@document.pdf"
```

Response:

```json
{
  "fileName": "ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
  "originalFileName": "document.pdf",
  "downloadUrl": "http://localhost:8080/api/v1/files/download/ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
  "fileType": "application/pdf",
  "fileSize": 242401,
  "uploadedAt": "2026-03-06T17:30:25.000Z"
}
```

- `GET /api/v1/files/download/{fileName}`

Returns the file as a binary download with the correct content type.

Example:
```bash
curl -fO http://localhost:8080/api/v1/files/download/ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf
```

- `GET /api/v1/files`

Example:
```bash
curl http://localhost:8080/api/v1/files
```

Response:

```json
[ 
  "ebbeedf7-8cad-47dc-acf8-cbb96dea83f5.pdf",
  "a1b2c3d4-1234-5678-abcd-ef1234567890.png"
]
```

[⬆ Back to the top](#uploader-api)