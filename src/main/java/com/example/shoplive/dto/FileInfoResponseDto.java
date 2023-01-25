package com.example.shoplive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class FileInfoResponseDto {

    Long id;

    String title;

    OriginalDto original;

    ResizedDto resized;

    ThumbnailDto thumbnail;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    OffsetDateTime createdAt;
}
