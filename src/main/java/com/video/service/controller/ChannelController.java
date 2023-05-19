package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.ChannelDto;
import com.video.service.entity.*;
import com.video.service.service.ChannelService;
import com.video.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping(value = "channel/findAll")
    public ApiResponseDto channelFindAll() {
        ApiResponseDto apiResponseDto = new ApiResponseDto();

        List<ChannelEntity> channels = channelService.channelFindAll();
        List<ChannelDto> result = channels.stream()
                .map(r -> new ChannelDto(r))
                .collect(Collectors.toList());
        System.out.println("result="+result.toString());

        return apiResponseDto;
    }

}
