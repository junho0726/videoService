package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.*;
import com.video.service.service.FileService;
import com.video.service.service.JwtService;
import com.video.service.service.UserService;
import com.video.service.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping(value = "video/insertProc")
    public ApiResponseDto insertVideo(@RequestHeader("Access_Token") String accessToken, @RequestPart(required = false) VideoEntity video, MultipartFile thumbnail, MultipartFile videoFile) {

        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);
/*
            if (!thumbnail.getContentType().startsWith("image/")) {
                response.setData("false");
                response.setCode("0001");
                response.setMessage("썸네일 이미지 파일이 아닙니다.");
                return response;
            }

            if (!videoFile.getContentType().startsWith("video/")) {
                response.setData("false");
                response.setCode("0001");
                response.setMessage("비디오 파일이 아닙니다.");
                return response;
            }*/

            try {
                List<VideoEntity> videoList = findUser.getChannel().getVideo();

                String thumbnailOriginalName = thumbnail.getOriginalFilename();
                String thumbnailFileName = UUID.randomUUID().toString() + "_" + thumbnailOriginalName;
                String thumbnailFolderPath = makeFolder();
                Path thumbnailSavePath = Paths.get(uploadPath, thumbnailFolderPath, thumbnailFileName);
                thumbnail.transferTo(thumbnailSavePath);

                String videoOriginalName = videoFile.getOriginalFilename();
                String videoFileName = UUID.randomUUID().toString() + "_" + videoOriginalName;
                String videoFolderPath = makeFolder();
                Path videoSavePath = Paths.get(uploadPath, videoFolderPath, videoFileName);
                videoFile.transferTo(videoSavePath);

                video.setChannel(findUser.getChannel());
                videoService.insertVideo(video);

                FileEntity thumbnailFile = new FileEntity();
                thumbnailFile.setGubn("thumbnail");
                thumbnailFile.setVideo(video);
                thumbnailFile.setFilePath(thumbnailFolderPath);
                thumbnailFile.setFileFullPath(thumbnailSavePath.toString());
                thumbnailFile.setFileOriginName(thumbnailOriginalName);
                fileService.insertFile(thumbnailFile);

                FileEntity videoFileEntity = new FileEntity();
                videoFileEntity.setGubn("videoFile");
                videoFileEntity.setVideo(video);
                videoFileEntity.setFilePath(videoFolderPath);
                videoFileEntity.setFileFullPath(videoSavePath.toString());
                videoFileEntity.setFileOriginName(videoOriginalName);
                fileService.insertFile(videoFileEntity);

                response.setData(video);
                response.setCode("0001");
                response.setMessage("성공");
            } catch (IOException e) {
                response.setCode("0001");
                response.setMessage("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/DD"));

        String folderPath = str.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
