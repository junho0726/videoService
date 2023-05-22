package com.video.service.dto;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.FileEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileDto
{

    private int fileSeq;

    private String gubn;

    private String fileFullPath;

    private String fileName;

    private String videoTitle;

    private String videoContent;

    private int videoCount;

        public FileDto(FileEntity fileEntity){
            fileSeq =fileEntity.getFileSeq();
            fileFullPath = fileEntity.getFileFullPath();
            fileName = fileEntity.getFileName();
        }

}
