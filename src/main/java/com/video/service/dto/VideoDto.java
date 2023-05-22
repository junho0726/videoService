package com.video.service.dto;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.FileEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;

@Data
public class VideoDto
{

    private int fileSeq;

    private String title;

    private String content;

    public VideoDto() {
        // 기본 생성자
    }
    public VideoDto(VideoEntity videoEntity){
            fileSeq =videoEntity.getFile().getFileSeq();
            title = videoEntity.getTitle();
            content = videoEntity.getContent();
        }

}
