package com.example.shoplive.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FileUploadResponseDto {
    Long id;

    HttpStatus httpStatus;
}
