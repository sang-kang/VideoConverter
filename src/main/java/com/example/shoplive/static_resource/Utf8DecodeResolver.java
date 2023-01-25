package com.example.shoplive.static_resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Utf8DecodeResolver extends PathResourceResolver implements ResourceResolver {
    private static final Logger logger = LoggerFactory.getLogger(Utf8DecodeResolver.class);

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);
        return super.getResource(resourcePath, location);
    }
}
