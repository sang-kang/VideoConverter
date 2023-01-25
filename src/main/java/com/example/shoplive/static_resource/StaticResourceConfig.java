package com.example.shoplive.static_resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.nio.file.Path;

@Configuration
@EnableWebMvc
public class StaticResourceConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfig.class);
    private static final String ROOT = "/path/to/**";
    private static final URI LOCATION = Path.of("c:", "shoplive_resource").toUri();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // FIXME: 운영체제 별
        registry
                .addResourceHandler(ROOT)
                // unix? setting
                //.addResourceLocations("file:/opt/files/")
                .addResourceLocations(LOCATION.toString())
                .resourceChain(true)
                .addResolver(new Utf8DecodeResolver());
    }
}