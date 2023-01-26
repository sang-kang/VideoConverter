package com.example.shoplive.entities.fileinfo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Table
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private Long originalFileSize;

    private Integer originalWidth;

    private Integer originalHeight;

    private String originalVideoUrl;

    private Long resizedFileSize;

    private Integer resizedWidth;

    private Integer resizedHeight;

    private String resizedVideoUrl;

    private String thumbnailImageUrl;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}