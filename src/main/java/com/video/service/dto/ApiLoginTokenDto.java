package com.video.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiLoginTokenDto {

    private int userSeq;

    private int channelSeq;

    private String accessToken;
}
