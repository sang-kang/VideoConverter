package com.example.shoplive.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);

    @Override
    public void initialize(ValidFile validFile) {
        logger.info("File validator initialized!!");
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        String contentType = multipartFile.getContentType();
        long size = multipartFile.getSize();
        assert contentType != null;
        return isSupportedContentType(contentType) && isLessOrEqual100MB(size);
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("video/mp4");
    }

    private boolean isLessOrEqual100MB(long size) {
        long oneHundredMegaBytes = 10000000L;
        return size <= oneHundredMegaBytes;
    }
}