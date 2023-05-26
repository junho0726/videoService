package com.video.service.dto;

import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeStateDto
{

    private String likeState;

    private int userSeq;

    private int videoSeq;

    private int likeCount;

}
