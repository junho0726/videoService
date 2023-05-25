package com.video.service.controller;

import com.video.service.dto.*;
import com.video.service.entity.*;
import com.video.service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class VideoController {

    @Value("${com.video.upload.path}")
    private String uploadPath;

    private final UserService userService;

    private final JwtService jwtService;

    private final VideoService videoService;

    private final FileService fileService;

    private final ThumbnailService thumbnailService;

    private final AwsS3Service awsS3Service;

    private final ChannelService channelService;

    private final LikeStateService likeStateService;

    @PostMapping(value = "video/fileInsert")
    public ApiResponseDto fileInsert(@RequestHeader("Access_Token") String accessToken, @RequestPart MultipartFile videoFile) {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            FileEntity fileEntity = awsS3Service.saveFile(videoFile);
            FileEntity saveFile = fileService.insertFile(fileEntity);

            response.setData(saveFile);
            response.setCode("0001");
            response.setMessage("성공");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "video/videoInsert")
    public ApiResponseDto videoInsert(@RequestHeader("Access_Token") String accessToken, @RequestBody VideoDto videoDto, MultipartFile thumbnail)  throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            VideoEntity videoEntity = new VideoEntity();
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileSeq(videoDto.getFileSeq());
            videoEntity.setFile(fileEntity);
            videoEntity.setTitle(videoDto.getTitle());
            videoEntity.setContent(videoDto.getContent());
            videoEntity.setChannel(findUser.getChannel());

            VideoEntity saveVideo = videoService.insertVideo(videoEntity);

            response.setData(saveVideo);
            response.setCode("0000");
            response.setMessage("성공");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }


   @PostMapping(value = "video/thumbnailInsert")
    public ApiResponseDto insertThumbnail(@RequestHeader("Access_Token") String accessToken, @RequestPart VideoEntity video, MultipartFile thumbnail) {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            if (!thumbnail.getContentType().startsWith("image")) {
                response.setData("false");
                response.setCode("0001");
                response.setMessage("이미지 파일 형식이 아닙니다.");
                return response;
            }

            ThumbnailEntity thumbnailFile = awsS3Service.saveThumbnail(thumbnail);
            ThumbnailEntity saveThumbnail = thumbnailService.insertThumbnail(thumbnailFile);

            video.setThumbnail(saveThumbnail);
            VideoEntity videoEntity = videoService.updateVideo(video);

            response.setData(videoEntity);
            response.setCode("0000");
            response.setMessage("성공");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    @GetMapping(value = "video/findAll")
    public ApiResponseDto videoFindAll(@RequestParam(defaultValue  = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(value = "channelSeq", required = false) Integer channelSeq) {
        ApiResponseDto response = new ApiResponseDto();
        Pageable pageable = PageRequest.of(page - 1 , size);
        try {
            Page<VideoEntity> videos;
            List<ApiFileDto> result;
            PaginationDto pagination = new PaginationDto();

            if (channelSeq != null) {
                ChannelEntity channelEntity = new ChannelEntity();
                int seq = channelSeq.intValue();
                channelEntity.setChannelSeq(seq);
                videos = videoService.findAllByChannel(channelEntity, pageable);
            } else {
                videos = videoService.findAll(pageable);
            }

            result = videos.getContent().stream()
                .map(video -> {
                    ApiFileDto apiFileDto = new ApiFileDto();
                    ChannelEntity channel = video.getChannel();
                    apiFileDto.setUserId(channel.getUser().getId());
                    apiFileDto.setUserName(channel.getUser().getName());
                    apiFileDto.setVideoSeq(video.getVideoSeq());
                    apiFileDto.setVideoTitle(video.getTitle());
                    apiFileDto.setVideoContent(video.getContent());
                    apiFileDto.setVideoCount(video.getCount());
                    FileEntity videoFile = fileService.findByVideo(video);
                    apiFileDto.setFileFullPath(videoFile.getFileFullPath());
                    apiFileDto.setFileName(videoFile.getFileName());
                    apiFileDto.setFileOriginName(videoFile.getFileOriginName());
                    ThumbnailEntity thumbnail = thumbnailService.findByVideo(video);
                    if (thumbnail != null) {
                        apiFileDto.setThumbnailFullPath(thumbnail.getFileFullPath());
                    }

                    return apiFileDto;
                })
                .collect(Collectors.toList());

            pagination.setPage(videos.getNumber() + 1);
            pagination.setSize(videos.getSize());
            pagination.setTotalCount(videos.getTotalElements());
            pagination.setTotalPages(videos.getTotalPages());

            response.setData(result);
            response.setCode("0000");
            response.setMessage("Successed!!");
            response.setPagination(pagination);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping(value = "video/findDetail/{videoSeq}")
    public ApiResponseDto videoFindDetail(@PathVariable("videoSeq") int videoSeq) {
        ApiResponseDto response = new ApiResponseDto();

        try {
            VideoEntity video = videoService.findById(videoSeq);
            FileEntity file = fileService.findByVideo(video);
            ChannelEntity channel = channelService.findById(video.getChannel().getChannelSeq());
            ThumbnailEntity thumbnail = thumbnailService.findByVideo(video);
            int likeStateCount = likeStateService.likeStateCountByVideo(video);
            ApiFileDto apiFileDto = new ApiFileDto();
            apiFileDto.setUserId(channel.getUser().getId());
            apiFileDto.setUserName(channel.getUser().getName());
            apiFileDto.setChannelName(channel.getChannelName());
            apiFileDto.setVideoSeq(video.getVideoSeq());
            apiFileDto.setVideoTitle(video.getTitle());
            apiFileDto.setVideoContent(video.getContent());
            apiFileDto.setVideoCount(video.getCount());
            apiFileDto.setFileFullPath(file.getFileFullPath());
            apiFileDto.setFileName(file.getFileName());
            apiFileDto.setFileOriginName(file.getFileOriginName());
            apiFileDto.setLikeStateCount(likeStateCount);
            if (thumbnail != null) {
                apiFileDto.setThumbnailFullPath(thumbnail.getFileFullPath());
            }
            response.setData(apiFileDto);
            response.setCode("0000");
            response.setMessage("Successed!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping(value = "video/findMyVideoAll")
    public ApiResponseDto findMyVideoAll(@RequestHeader("Access_Token") String accessToken, @RequestParam(defaultValue  = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        ApiResponseDto response = new ApiResponseDto();
        Pageable pageable = PageRequest.of(page - 1 , size);
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);
            Page<VideoEntity> videos = videoService.findAllByChannel(findUser.getChannel(), pageable);
            List<ApiFileDto> result = videos.getContent().stream()
                .map(video -> {
                    ApiFileDto apiFileDto = new ApiFileDto();
                    ChannelEntity channel = video.getChannel();
                    apiFileDto.setUserId(channel.getUser().getId());
                    apiFileDto.setUserName(channel.getUser().getName());
                    apiFileDto.setVideoSeq(video.getVideoSeq());
                    apiFileDto.setVideoTitle(video.getTitle());
                    apiFileDto.setVideoContent(video.getContent());
                    apiFileDto.setVideoCount(video.getCount());
                    FileEntity videoFile = fileService.findByVideo(video);
                    apiFileDto.setFileFullPath(videoFile.getFileFullPath());
                    apiFileDto.setFileName(videoFile.getFileName());
                    apiFileDto.setFileOriginName(videoFile.getFileOriginName());
                    return apiFileDto;
                })
                .collect(Collectors.toList());

            PaginationDto pagination = new PaginationDto();
            pagination.setPage(videos.getNumber()+1);
            pagination.setSize(videos.getSize());
            pagination.setTotalCount(videos.getTotalElements());
            pagination.setTotalPages(videos.getTotalPages());

            response.setData(result);
            response.setCode("0000");
            response.setMessage("Successed!!");
            response.setPagination(pagination);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

}
