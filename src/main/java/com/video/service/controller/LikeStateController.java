package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.LikeStateDto;
import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.UserEntity;
import com.video.service.service.JwtService;
import com.video.service.service.LikeStateService;
import com.video.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LikeStateController {

    private final LikeStateService likeStateService;

    private final JwtService jwtService;

    private final UserService userService;

    @PostMapping(value = "like/likeStateInsert")
    public ApiResponseDto likeStateInsert(@RequestHeader("Access_Token") String accessToken, @RequestBody LikeStateDto likeStateDto) throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try{
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            likeStateDto.setUserSeq(findUser.getUserSeq());
            LikeStateDto likeState = likeStateService.likeInsert(likeStateDto);
            response.setData(likeState);
            response.setCode("0000");
            response.setMessage("Successed!!");
        }catch (Exception e){
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("좋아요/싫어요 실패");
        }
        return response;
    }
}
