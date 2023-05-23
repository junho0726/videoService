package com.video.service.dto;

import com.video.service.entity.VideoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginationDto
{
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;
}
