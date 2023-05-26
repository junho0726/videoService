package com.video.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiFileDto {

    private String userId;

    private String userName;

    private String channelName;

    private int videoSeq ;

    private String videoTitle ;

    private String videoContent;

    private int videoCount ;

    private String fileFullPath;

    private  String fileName;

    private String fileOriginName ;

    private String thumbnailFullPath;

    private int likeStateCount;

    private String likeState;



}
