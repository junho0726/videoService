package com.video.service.entity;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private String Code;
    private Object data;
}
