package com.example.shoplive.file_conversion_async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class FileConversionAsyncException implements AsyncUncaughtExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(FileConversionAsyncException.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        logger.info("Exception message - " + throwable.getMessage());
        logger.info("Method name - " + method.getName());
        for (Object param : obj) {
            logger.info("Parameter value - " + param);
        }
    }
}
