package com.video.service.dto;

import lombok.Data;

@Data
public class ApiResponseDto {
    private String message;
    private String Code;
    private Object data;
    private PaginationDto pagination;
}
