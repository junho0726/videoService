package com.video.service.dto;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.FileEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FileDto
{

    private String fileFullPath;

    private String fileOriginName;

    private String fileName;

    private int videoSeq;

    private String videoTitle;

    private String videoContent;

    private int videoCount;

}
