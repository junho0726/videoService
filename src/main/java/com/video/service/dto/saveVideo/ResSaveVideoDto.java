package com.video.service.dto.saveVideo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResSaveVideoDto {

    private int saveVideoSeq;
    private String state;
}
