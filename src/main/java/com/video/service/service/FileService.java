package com.video.service.service;


import com.video.service.dto.FileDto;
import com.video.service.entity.FileEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileEntity insertFile(FileEntity fileEntity) throws Exception {
        return fileRepository.save(fileEntity);
    }

    public FileEntity findByVideo(VideoEntity videoEntity) {
        Optional <FileEntity> file  = fileRepository.findById(videoEntity.getFile().getFileSeq());

        return file.get();
    }

}
