package com.video.service.controller;


import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.ChannelEntity;
import com.video.service.entity.UserEntity;
import com.video.service.service.ChannelService;
import com.video.service.service.JwtService;
import com.video.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ChannelService channelService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping(value = "user/joinProc")
    public ApiResponseDto userJoin(@RequestBody UserEntity user, HttpServletRequest req) throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            String enPw = user.getPw();
            String encryptedPassword = passwordEncoder.encode(enPw);
            user.setPw(encryptedPassword);
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null) ip = req.getRemoteAddr();
            user.setIp(ip);
            UserEntity savedUser = userService.userJoin(user);
            String accessToken = jwtService.createToken( savedUser.getId(), "Access");
            String refreshToken = jwtService.createToken( savedUser.getId(), "Refresh");
            savedUser.setRefreshToken(refreshToken);
            savedUser.setAccessToken(accessToken);
            UserEntity tokenSavedUser = userService.userJoin(user);
            if (tokenSavedUser != null) {
                response.setCode("0000");
                response.setMessage("Successed!!");
                response.setData(tokenSavedUser);
                ChannelEntity channel = new ChannelEntity();
                channel.setUser(tokenSavedUser);
                channelService.ChannelSave(channel);
            } else {
                response.setCode("0001");
                response.setMessage("Failed to join user");
            }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;

    }

    @PostMapping(value = "user/updateProc")
    public ApiResponseDto userUpdate(@RequestBody UserEntity user, HttpServletRequest req) throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            String enPw = user.getPw();
            String encryptedPassword = passwordEncoder.encode(enPw);
            user.setPw(encryptedPassword);
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null) ip = req.getRemoteAddr();
            user.setIp(ip);
            UserEntity updateUser = userService.userUpdate(user);
            if (updateUser != null) {
                response.setCode("0000");
                response.setMessage("Successed!!");
                response.setData(updateUser);
                ChannelEntity channel = new ChannelEntity();
                channel.setUser(updateUser);
            } else {
                response.setCode("0001");
                response.setMessage("Failed to update user");
            }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;

    }

    @PostMapping(value ="user/login")
    public ApiResponseDto userLogin(@RequestBody UserEntity user, HttpServletRequest req) throws Exception{
        ApiResponseDto response = new ApiResponseDto();
        try{
            UserEntity findUser = userService.findByid(user);
            if (findUser != null){
                if(BCrypt.checkpw(user.getPw(), findUser.getPw())){
                        response.setCode("0000");
                        response.setMessage("Successed!!");
                        response.setData(findUser);
                    }else{
                        response.setCode("0001");
                        response.setMessage("Incorrect password");
                    }
            }else{
                response.setCode("0001");
                response.setMessage("Could not found user");
            }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;
    }


    @PostMapping(value ="user/checkId")
    public ApiResponseDto checkId(@RequestBody UserEntity user, HttpServletRequest req) throws Exception{
        ApiResponseDto response = new ApiResponseDto();
        try{
            boolean checkId = userService.checkId(user);
            if (checkId == true){
                    response.setCode("0000");
                    response.setMessage("사용가능한 아이디입니다.");
                    response.setData("true");
                }else{
                    response.setCode("0001");
                    response.setMessage("이미 아이디가 존재합니다.");
                }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;
    }

}
