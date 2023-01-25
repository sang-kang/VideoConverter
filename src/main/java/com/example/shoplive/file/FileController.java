package com.example.shoplive.file;

import com.example.shoplive.dto.FileInfoResponseDto;
import com.example.shoplive.dto.FileUploadResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String home() {
        return "Hello, This is shoplive";
    }

    /**
     * 영상 업로드 및 변환 API
     **/
    @PostMapping
    public FileUploadResponseDto handleFileUpload(@ValidFile @RequestParam("file") MultipartFile file, @RequestParam("title") String title) throws Exception {
        return this.fileService.handleFileUpload(file, title);
    }

    /**
     * 영상의 상세 정보를 조회할 수 있는 API
     **/
    @GetMapping("/video/{id}")
    public FileInfoResponseDto getDetail(@PathVariable("id") String fileInfoId) throws JsonProcessingException {
        return this.fileService.getDetail(fileInfoId);
    }
}