package com.video.service.controller;

import com.video.service.dto.*;
import com.video.service.entity.*;
import com.video.service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private final ThumbnailService thumbnailService;

    @PostMapping(value = "video/fileInsert")
    public ApiResponseDto fileInsert(@RequestHeader("Access_Token") String accessToken, @RequestPart MultipartFile videoFile) {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            String videoOriginalName = videoFile.getOriginalFilename();
            String videoFileName = UUID.randomUUID().toString() + "_" + videoOriginalName;
            String videoFolderPath = makeFolder();
            Path videoSavePath = Paths.get(uploadPath, videoFolderPath, videoFileName);
            videoFile.transferTo(videoSavePath);

            FileEntity videoFileEntity = new FileEntity();
            videoFileEntity.setFilePath(videoFolderPath);
            videoFileEntity.setFileFullPath(videoSavePath.toString());
            videoFileEntity.setFileOriginName(videoOriginalName);
            videoFileEntity.setFileName(videoFileName);
            FileEntity saveFile = fileService.insertFile(videoFileEntity);

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
            response.setCode("0001");
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

            if (!thumbnail.getContentType().startsWith("image/")) {
                response.setData("false");
                response.setCode("0001");
                response.setMessage("이미지 파일 형식이 아닙니다.");
                return response;
            }

            String thumbnailOriginalName = thumbnail.getOriginalFilename();
            String thumbnailFileName = UUID.randomUUID().toString() + "_" + thumbnailOriginalName;
            String thumbnailFolderPath = makeFolder();
            Path thumbnailSavePath = Paths.get(uploadPath, thumbnailFolderPath, thumbnailFileName);
            thumbnail.transferTo(thumbnailSavePath);

            ThumbnailEntity thumbnailFile = new ThumbnailEntity();
            thumbnailFile.setFilePath(thumbnailFolderPath);
            thumbnailFile.setFileFullPath(thumbnailSavePath.toString());
            thumbnailFile.setFileOriginName(thumbnailOriginalName);
            thumbnailFile.setFileName(thumbnailFileName);
            ThumbnailEntity saveThumbnail = thumbnailService.insertThumbnail(thumbnailFile);

            video.setThumbnail(saveThumbnail);
            videoService.updateVideo(video);

            response.setCode("0001");
            response.setMessage("성공");

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
