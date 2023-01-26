package com.example.shoplive.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FFmpegConfig {
    private static final Logger logger = LoggerFactory.getLogger(FFmpegConfig.class);

    @Bean
    public FFmpeg ffmpeg() throws IOException {
        return new FFmpeg("/opt/shoplive/ffmpeg-5.1.1-amd64-static/ffmpeg");
    }

    @Bean
    public FFprobe ffprobe() throws IOException {
        return new FFprobe("/opt/shoplive/ffmpeg-5.1.1-amd64-static/ffprobe");
    }

}