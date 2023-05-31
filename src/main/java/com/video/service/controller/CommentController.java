package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.CommentDto;
import com.video.service.entity.CommentEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.service.CommentService;
import com.video.service.service.JwtService;
import com.video.service.service.UserService;
import com.video.service.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final JwtService jwtService;

    private final UserService userService;

    private final VideoService videoService;

    @PostMapping(value = "comment/save")
    public ApiResponseDto insertComment(@RequestHeader("Access_Token") String accessToken, @RequestBody CommentDto commentDto) throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);
            commentDto.setUserSeq(findUser.getUserSeq());
            CommentEntity comment = commentService.insertComment(commentDto);
            response.setData(comment);
            response.setCode("0000");
            response.setMessage("성공");
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;
    }

    @GetMapping(value = "comment/parent/list")
    public ApiResponseDto CommentParentList(@RequestParam int videoSeq) throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            VideoEntity videoEntity = videoService.findById(videoSeq);
            response.setData(commentService.findParentByVideoEntity(videoEntity));
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
