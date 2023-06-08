package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.saveVideo.ReqSaveVideoDto;
import com.video.service.entity.SaveVideoEntity;
import com.video.service.entity.UserEntity;
import com.video.service.service.JwtService;
import com.video.service.service.SaveVideoService;
import com.video.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/saveVideo/")
public class SaveVideoController {

    @Autowired
    private SaveVideoService saveVideoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("handleState")
    public ApiResponseDto handleState(@RequestHeader("Access_Token") String accessToken, @RequestBody ReqSaveVideoDto reqSaveVideoDto) {
        ApiResponseDto response = new ApiResponseDto();

        try {
            Map resultMap = jwtService.getSubject(accessToken);
            UserEntity findUser = userService.findByid(UserEntity.builder().id(resultMap.get("fdId").toString()).build());
            reqSaveVideoDto.setUserSeq(findUser.getUserSeq());

            response.setData(saveVideoService.handle(reqSaveVideoDto));
            response.setCode("0000");
            response.setMessage("Successed!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.setData("로그인 후 이용해주세요.");
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }
}
