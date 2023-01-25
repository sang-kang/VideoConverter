package com.example.shoplive;

import com.example.shoplive.storage.StorageProperties;
import com.example.shoplive.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync    // This switches on Spring's ability to run @Async method in a back ground thread pool.
public class ShopliveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopliveApplication.class, args);
    }

    // FIXME: 재부팅 할떄마다 stroageService에 있는거 다 지우기 위해서 존재
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
