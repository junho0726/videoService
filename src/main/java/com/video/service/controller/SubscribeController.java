package com.video.service.controller;


import com.video.service.dto.ApiResponseDto;
import com.video.service.dto.ChannelDto;
import com.video.service.dto.LikeStateDto;
import com.video.service.dto.SubscribeDto;
import com.video.service.entity.SubscribeEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.service.JwtService;
import com.video.service.service.SubscribeService;
import com.video.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class SubscribeController {

    private final UserService userService;

    private final JwtService jwtService;

    private final SubscribeService subscribeSerive;

    @PostMapping(value = "subscribe/save")
    public ApiResponseDto insertSubscribe(@RequestHeader("Access_Token") String accessToken, @RequestBody SubscribeDto subscribeDto) {
        ApiResponseDto response = new ApiResponseDto();
        try {
        UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserSeq(findUser.getUserSeq());
            subscribeDto.setUser(userEntity);

            ModelMapper modelMapper = new ModelMapper();
            SubscribeEntity subscribeEntity = modelMapper.map(subscribeDto, SubscribeEntity.class);
            SubscribeDto subscribe = subscribeSerive.insertSubscribe(subscribeEntity);

            response.setData(subscribe);
            response.setCode("0000");
            response.setMessage("성공");
      }catch (Exception e){
          e.printStackTrace();
          response.setCode("0001");
          response.setMessage("Error :" + e.getMessage());
      }
      return response;
    }


    @GetMapping(value = "subscribe/list")
    public ApiResponseDto subscribeList(@RequestHeader("Access_Token") String accessToken) {
        ApiResponseDto response = new ApiResponseDto();
        try {
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);

            SubscribeEntity subscribeEntity = new SubscribeEntity();
            List<ChannelDto> channelDtos = subscribeSerive.findByUserSeq(findUser.getUserSeq());
            response.setData(channelDtos);
            response.setCode("0000");
            response.setMessage("성공");
        }catch (Exception e){
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;
    }



}
