package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.*;
import com.video.service.service.FileService;
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
import java.util.UUID;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class VideoController {

    @Value("${com.video.upload.path}")
    private String uploadPath;

    private final VideoService videoService;

    private final FileService fileService;

    @PostMapping(value = "insertVideo")
    public ApiResponseDto insertVideo(@RequestPart VideoEntity video, MultipartFile [] uploadFiles) throws Exception{
        ApiResponseDto response = new ApiResponseDto();
        try{
            FileEntity file = new FileEntity();
            for(MultipartFile uploadFile : uploadFiles){
                if(uploadFile.getContentType().startsWith("video") == false){
                    response.setData("false");
                    response.setCode("0001");
                    response.setMessage("비디오 파일이 아닙니다.");
                    return response;
                }
                String originalName = uploadFile.getOriginalFilename();
                String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
                String folderPath = makeFolder();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                try{
                    uploadFile.transferTo(savePath);
                    VideoEntity dbVideo = videoService.insertVideo(video);
                    file.setVideo(dbVideo);
                    file.setFilePath(folderPath);
                    file.setFileFullPath(saveName);
                    file.setFileOriginName(originalName);
                    fileService.insertFile(file);

                    response.setData(dbVideo);
                    response.setCode("0001");
                    response.setMessage("성공");
                }catch(IOException e){
                    response.setCode("0001");
                    response.setMessage("Error :" + e.getMessage());
                    return response;
                }
            }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
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
