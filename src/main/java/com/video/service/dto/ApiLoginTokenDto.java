package com.video.service.dto;

import lombok.Data;

@Data
public class ApiLoginTokenDto {
    private int userSeq;
    private String accessToken;
}
