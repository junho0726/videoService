package com.video.service.controller;


import com.video.service.dto.ApiLoginTokenDto;
import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.ChannelEntity;
import com.video.service.entity.TokenEntity;
import com.video.service.entity.UserEntity;
import com.video.service.service.ChannelService;
import com.video.service.service.JwtService;
import com.video.service.service.TokenService;
import com.video.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ChannelService channelService;

    private final JwtService jwtService;

    private final TokenService tokenService;

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
            if (savedUser != null) {
                response.setCode("0000");
                response.setMessage("Successed!!");
                response.setData(savedUser);
                ChannelEntity channel = new ChannelEntity();
                channel.setUser(savedUser);
                channelService.channelSave(channel);
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
                        String accessToken = jwtService.createToken(findUser.getId());
                        TokenEntity tokenEntity = new TokenEntity();
                        tokenEntity.setAccessToken(accessToken);
                        tokenEntity.setUser(findUser);
                        TokenEntity checkToken = tokenService.findByUserSeq(findUser.getUserSeq());
                        if(checkToken == null){
                             tokenService.tokenInsert(tokenEntity);
                        }else{
                            tokenService.updateAccessToken(tokenEntity);
                        }
                        userService.userJoin(findUser);
                        ApiLoginTokenDto loginTokenDto = new ApiLoginTokenDto();
                        loginTokenDto.setAccessToken(accessToken);
                        loginTokenDto.setUserSeq(findUser.getUserSeq());
                        response.setCode("0000");
                        response.setMessage("Successed!!");
                        response.setData(loginTokenDto);
                    }else{
                        response.setCode("0001");
                        response.setMessage("Incorrect password");
                    }
            }else{
                response.setCode("0001");
                response.setMessage("Could not found user");
            }
        }catch (Exception e){
            e.getMessage();
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

    @PostMapping(value ="user/info")
    public ApiResponseDto userInfo(@RequestHeader("Access_Token") String accessToken) throws Exception{
        ApiResponseDto response = new ApiResponseDto();
        try{
            UserEntity user = new UserEntity();
            Map resultMap = jwtService.getSubject(accessToken);
            user.setId(resultMap.get("fdId").toString());
            UserEntity findUser = userService.findByid(user);
            if(findUser != null){
                response.setCode("0000");
                response.setMessage("유저 조회 성공");
                response.setData(findUser);
            }else{
                response.setCode("0001");
                response.setMessage("유저 조회 실패");
            }
        }catch (Exception e){
            response.setCode("0001");
            response.setMessage("Error :" + e.getMessage());
        }
        return response;
    }


}
