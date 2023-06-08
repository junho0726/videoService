package com.video.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiFileDto {

    private int channelSeq;

    private String userId;

    private String userName;

    private String channelName;

    private int videoSeq ;

    private String videoTitle ;

    private String videoContent;

    private int videoCount ;

    private String saveVideoState;

    private int saveVideoStateSeq;

    private String fileFullPath;

    private  String fileName;

    private String fileOriginName ;

    private String thumbnailFullPath;

    private int likeStateCount;

    private String likeState;

    private int subscribeCount;

    private String subscribeState;

    private List<CommentDto> comment;
}
