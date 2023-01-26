package com.example.shoplive.file;

import com.example.shoplive.dto.*;
import com.example.shoplive.entities.fileinfo.FileInfo;
import com.example.shoplive.entities.fileinfo.FileInfoService;
import com.example.shoplive.ffmpeg.FFmpegUtil;
import com.example.shoplive.file_conversion_async.FIleConversionAsyncService;
import com.example.shoplive.storage.StorageProperties;
import com.example.shoplive.storage.StorageService;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private static final String UPLOAD_PATH = "http://localhost:8080/upload/";
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final StorageService storageService;
    private final StorageProperties storageProperties;
    private final FileInfoService fileInfoService;
    private final FIleConversionAsyncService fIleConversionAsyncService;
    private final FFmpegUtil fFmpegUtil;

    @Autowired
    public FileService(StorageService storageService, StorageProperties storageProperties, FileInfoService fileInfoService, FIleConversionAsyncService fIleConversionAsyncService, FFmpegUtil fFmpegUtil) {
        this.storageService = storageService;
        this.storageProperties = storageProperties;
        this.fileInfoService = fileInfoService;
        this.fIleConversionAsyncService = fIleConversionAsyncService;
        this.fFmpegUtil = fFmpegUtil;
    }

    public FileInfoResponseDto getDetail(String fileInfoId) {
        FileInfo fileInfo = this.fileInfoService.findById(Long.parseLong(fileInfoId));
        if (fileInfo == null) {
            throw new RuntimeException("Id" + fileInfoId + "NOT FOUND");
        }

        OriginalDto originalDto = new OriginalDto();
        originalDto.setFileSize(fileInfo.getOriginalFileSize());
        originalDto.setWidth(fileInfo.getOriginalWidth());
        originalDto.setHeight(fileInfo.getOriginalHeight());
        originalDto.setVideoUrl(fileInfo.getOriginalVideoUrl());

        ResizedDto resizedDto = new ResizedDto();
        resizedDto.setFileSize(fileInfo.getResizedFileSize());
        resizedDto.setWidth(fileInfo.getResizedWidth());
        resizedDto.setHeight(fileInfo.getResizedHeight());
        resizedDto.setVideoUrl(fileInfo.getResizedVideoUrl());

        ThumbnailDto thumbnailDto = new ThumbnailDto();
        thumbnailDto.setImageUrl(fileInfo.getThumbnailImageUrl());

        FileInfoResponseDto fileInfoResponseDto = new FileInfoResponseDto();
        fileInfoResponseDto.setId(fileInfo.getId());
        fileInfoResponseDto.setTitle(fileInfo.getTitle());
        fileInfoResponseDto.setOriginal(originalDto);
        fileInfoResponseDto.setResized(resizedDto);
        fileInfoResponseDto.setThumbnail(thumbnailDto);
        fileInfoResponseDto.setCreatedAt(fileInfo.getCreatedAt());

        return fileInfoResponseDto;
    }

    public FileUploadResponseDto handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) throws Exception {
        storageService.store(file);

        String uploadedFilePath = storageProperties.getLocation() + "/" + file.getOriginalFilename();
        FFmpegProbeResult originalProveResult = fFmpegUtil.getProveResult(uploadedFilePath);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setTitle(title);
        fileInfo.setOriginalFileSize(originalProveResult.format.size);
        fileInfo.setOriginalWidth(originalProveResult.getStreams().get(0).width);
        fileInfo.setOriginalHeight(originalProveResult.getStreams().get(0).height);
        fileInfo.setOriginalVideoUrl(UPLOAD_PATH + file.getOriginalFilename());
        FileInfo savedFileInfo = fileInfoService.save(fileInfo);

        logger.info("before getThumbnail() called");
        fIleConversionAsyncService.getThumbnail(uploadedFilePath, savedFileInfo);
        logger.info("before getThumbnail() called");

        logger.info("before convertFile() called");
        fIleConversionAsyncService.convertFile(uploadedFilePath, savedFileInfo);
        logger.info("after convertFile() called");

        FileUploadResponseDto fileUploadResponseDto = new FileUploadResponseDto();
        fileUploadResponseDto.setId(savedFileInfo.getId());
        fileUploadResponseDto.setHttpStatus(HttpStatus.OK);

        return fileUploadResponseDto;
    }
}
