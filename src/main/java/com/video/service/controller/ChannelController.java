package com.video.service.controller;

import com.video.service.dto.ApiFileDto;
import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.ChannelDto;
import com.video.service.dto.FileDto;
import com.video.service.entity.*;
import com.video.service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    private final UserService userService;

    private final JwtService jwtService;

    private final FileService fileService;

    private final SubscribeService subscribeService;
    @GetMapping("channel/detail/{channelSeq}")
    public ApiResponseDto channelDetail(@RequestHeader(value = "Access_Token", required = false) String accessToken, @PathVariable(required = false) int channelSeq) {
        ApiResponseDto response = new ApiResponseDto();
        UserEntity user = new UserEntity();

        try {
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            ChannelDto channelDto = null;
            if (channelSeq == Integer.parseInt("0")) {
                channelDto = new ChannelDto(channelService.findByUserSeq(findUser.getUserSeq()));
                channelDto.setMyChannel(true);
            } else {
                channelDto = new ChannelDto(channelService.findById(channelSeq));
                if(channelDto.getUserId().equals(findUser.getId())) {
                    channelDto.setMyChannel(true);
                } else {
                    channelDto.setMyChannel(false);
                }
            }
            channelDto.setSubscribeCount(subscribeService.subscribeCountByChannel(channelDto.getChannelSeq()));
            response.setData(channelDto);
            response.setCode("0000");
            response.setMessage("성공");
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }

        return response;
    }
}