package com.example.shoplive.file_conversion_async;

import com.example.shoplive.entities.fileinfo.FileInfo;
import com.example.shoplive.entities.fileinfo.FileInfoService;
import com.example.shoplive.ffmpeg.FFmpegUtil;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FIleConversionAsyncService {

    private static final String RESIZED_PATH = "http://localhost:8080/resized/";
    private static final String THUMBNAIL_PATH = "http://localhost:8080/thumbnail/";
    private static final Logger logger = LoggerFactory.getLogger(FIleConversionAsyncService.class);
    private final FFmpegUtil fFmpegUtil;
    private final FileInfoService fileInfoService;

    @Autowired
    public FIleConversionAsyncService(FFmpegUtil fFmpegUtil, FileInfoService fileInfoService) {
        this.fFmpegUtil = fFmpegUtil;
        this.fileInfoService = fileInfoService;
    }

    @Async
    public void convertFile(String uploadedFilePath, FileInfo savedFileInfo) {
        FFmpegProbeResult resizedProbeResult = fFmpegUtil.convertVideoProp(uploadedFilePath);

        String[] splitBySlash = uploadedFilePath.split("/");
        String afterLastSlash = splitBySlash[splitBySlash.length - 1];
        String name = afterLastSlash.substring(0, afterLastSlash.indexOf("."));
        String format = ".mp4";

        FileInfo fileInfo = this.fileInfoService.findById(savedFileInfo.getId());
        fileInfo.setResizedFileSize(resizedProbeResult.format.size);
        fileInfo.setResizedWidth(resizedProbeResult.getStreams().get(0).width);
        fileInfo.setResizedHeight(resizedProbeResult.getStreams().get(0).height);
        fileInfo.setResizedVideoUrl(RESIZED_PATH + name + format);
        this.fileInfoService.save(fileInfo);

        logger.info("convertFile async test: " + Thread.currentThread());
    }

    @Async
    public void getThumbnail(String uploadedFilePath, FileInfo savedFileInfo) {
        FFmpegProbeResult thumbnail = fFmpegUtil.getThumbnail(uploadedFilePath);

        String[] splitBySlash = uploadedFilePath.split("/");
        String afterLastSlash = splitBySlash[splitBySlash.length - 1];
        String name = afterLastSlash.substring(0, afterLastSlash.indexOf("."));
        String format = ".png";

        FileInfo fileInfo = this.fileInfoService.findById(savedFileInfo.getId());
        fileInfo.setThumbnailImageUrl(THUMBNAIL_PATH + name + format);
        this.fileInfoService.save(fileInfo);

        logger.info("getThumbnail async test: " + Thread.currentThread());
    }
}