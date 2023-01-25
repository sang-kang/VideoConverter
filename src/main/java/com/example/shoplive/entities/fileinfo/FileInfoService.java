package com.example.shoplive.entities.fileinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileInfoService {

    private static final Logger logger = LoggerFactory.getLogger(FileInfoService.class);
    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public FileInfoService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo save(FileInfo fileInfo) {
        return this.fileInfoRepository.save(fileInfo);
    }

    public FileInfo findById(long id) {
        return this.fileInfoRepository.findById(id);
    }
}
