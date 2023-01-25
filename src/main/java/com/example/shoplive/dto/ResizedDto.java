package com.example.shoplive.dto;

import lombok.Data;

@Data
public class ResizedDto {

    private Long fileSize;

    private Integer width;

    private Integer height;

    private String videoUrl;
}
