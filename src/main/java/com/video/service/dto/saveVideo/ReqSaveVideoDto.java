package com.video.service.dto.saveVideo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqSaveVideoDto {

    private int saveVideoSeq;

    private String state;

    private int videoSeq;

    private int userSeq;
}
