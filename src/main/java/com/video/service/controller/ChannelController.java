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


}