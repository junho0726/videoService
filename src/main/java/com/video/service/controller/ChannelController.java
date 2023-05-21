package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.ChannelDto;
import com.video.service.dto.FileDto;
import com.video.service.entity.*;
import com.video.service.service.ChannelService;
import com.video.service.service.FileService;
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

    private final FileService fileService;

    @PostMapping(value = "channel/findAll")
    public ApiResponseDto channelFindAll() {
        ApiResponseDto apiResponseDto = new ApiResponseDto();

        try {
            List<ChannelEntity> channels = channelService.channelFindAll();
            List<ChannelDto> result = channels.stream()
                    .map(channel -> {
                        ChannelDto channelDto = new ChannelDto(channel);
                        List<VideoEntity> videos = channel.getVideo();
                        List<FileDto> fileDtos = new ArrayList<>();
                        for (VideoEntity video : videos) {
                            List<FileDto> videoFile = fileService.findByVideo(video);
                            fileDtos.addAll(videoFile);
                        }
                        channelDto.setFiles(fileDtos);
                        return channelDto;
                    })
                    .collect(Collectors.toList());

            List<Object> dataList = new ArrayList<>();
            for (ChannelDto channelDto : result) {
                Map<String, Object> dataItem = new HashMap<>();
                dataItem.put("channelSeq", channelDto.getChannelSeq());
                dataItem.put("userId", channelDto.getUserId());
                dataItem.put("userName", channelDto.getUserName());
                List<Map<String, Object>> filesList = new ArrayList<>();
                for (FileDto fileDto : channelDto.getFiles()) {
                    Map<String, Object> fileItem = new HashMap<>();
                    fileItem.put("fileSeq", fileDto.getFileSeq());
                    fileItem.put("gubn", fileDto.getGubn());
                    fileItem.put("fileFullPath", fileDto.getFileFullPath());
                    fileItem.put("fileName", fileDto.getFileName());
                    dataItem.put("videoTitle", fileDto.getVideoTitle());
                    dataItem.put("videoContent", fileDto.getVideoContent());
                    dataItem.put("videoCount", fileDto.getVideoCount());
                    filesList.add(fileItem);
                }
                dataItem.put("files", filesList);
                dataList.add(dataItem);
            }
            apiResponseDto.setData(dataList);
            apiResponseDto.setCode("0000");
            apiResponseDto.setMessage("Successed!!");
        } catch (Exception e) {
            // 예외 처리 코드
            e.printStackTrace();
            // 예외 처리 후 필요한 작업 수행
        }

        return apiResponseDto;
    }
}
