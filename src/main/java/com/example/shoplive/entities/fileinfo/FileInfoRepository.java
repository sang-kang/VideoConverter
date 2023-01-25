package com.example.shoplive.entities.fileinfo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends CrudRepository<FileInfo, Long> {

    FileInfo save(FileInfo fileInfo);

    FileInfo findById(long id);
}