package com.video.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto
{

    private int videoSeq;

    private String videoTitle;

    private String videoContent;

}
