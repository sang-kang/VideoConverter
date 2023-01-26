package com.example.shoplive.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FFmpegUtil {
    private static final Logger logger = LoggerFactory.getLogger(FFmpegUtil.class);
    private static final String RESIZED_OUTPUT_PATH = "/opt/shoplive/shoplive_resource/resized/";
    private static final String THUMBNAIL_OUTPUT_PATH = "/opt/shoplive/shoplive_resource/thumbnail/";
    private final FFmpeg ffMpeg;
    private final FFprobe ffProbe;

    @Autowired
    public FFmpegUtil(FFmpeg ffMpeg, FFprobe ffProbe) {
        this.ffMpeg = ffMpeg;
        this.ffProbe = ffProbe;
    }

    public FFmpegProbeResult getProveResult(String filePath) {
        FFmpegProbeResult fFmpegProbeResult;

        try {
            fFmpegProbeResult = ffProbe.probe(filePath);
            FFmpegFormat format = fFmpegProbeResult.getFormat();
            logger.info("filename:  " + format.filename);
            logger.info("format_long_name:  " + format.format_long_name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fFmpegProbeResult;
    }

    public FFmpegProbeResult convertVideoProp(String uploadedFilePath) {
        String[] splitBySlash = uploadedFilePath.split("/");
        String afterLastSlash = splitBySlash[splitBySlash.length - 1];
        String name = afterLastSlash.substring(0, afterLastSlash.indexOf("."));
        String format = ".mp4";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(uploadedFilePath)
                .overrideOutputFiles(true)
                .addOutput(RESIZED_OUTPUT_PATH + name + format)
                .setFormat("mp4")
                .setVideoCodec("libx264")
                .setVideoFilter("scale=360:trunc(ow/a/2)*2")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffMpeg, ffProbe);
        FFmpegJob job = executor.createJob(builder);
        job.run();

        if (job.getState() != FFmpegJob.State.FINISHED) {
            throw new RuntimeException("can not convert");
        }
        return getProveResult(RESIZED_OUTPUT_PATH + name + format);
    }

    public FFmpegProbeResult getThumbnail(String uploadedFilePath) {
        String[] splitBySlash = uploadedFilePath.split("/");
        String afterLastSlash = splitBySlash[splitBySlash.length - 1];
        String name = afterLastSlash.substring(0, afterLastSlash.indexOf("."));
        String format = ".png";

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(uploadedFilePath)
                .addExtraArgs("-ss", "00:00:00")
                .addOutput(THUMBNAIL_OUTPUT_PATH + name + format)
                .setFrames(1)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffMpeg, ffProbe);
        FFmpegJob job = executor.createJob(builder);
        job.run();

        if (job.getState() != FFmpegJob.State.FINISHED) {
            throw new RuntimeException("can not get thumbnail");
        }
        return getProveResult(THUMBNAIL_OUTPUT_PATH + name + format);
    }
}
