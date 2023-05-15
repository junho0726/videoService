package com.video.service.controller;


import com.video.service.entity.ApiResponse;
import com.video.service.entity.Channel;
import com.video.service.entity.User;
import com.video.service.service.ChannelService;
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

    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "joinProc")
    public ApiResponse userJoin(@RequestBody User user, HttpServletRequest req) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            String enPw = user.getPw();
            String encryptedPassword = passwordEncoder.encode(enPw);
            user.setPw(encryptedPassword);
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null) ip = req.getRemoteAddr();
            user.setIp(ip);
            User savedUser = userService.userJoin(user);
            if (savedUser != null) {
                response.setCode("0000");
                response.setMessage("Successed!!");
                response.setData(savedUser);

                Channel channel = new Channel();
                channel.setUser(savedUser);
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

    @PostMapping(value = "updateProc")
    public ApiResponse userUpdate(@RequestBody User user, HttpServletRequest req) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            String enPw = user.getPw();
            String encryptedPassword = passwordEncoder.encode(enPw);
            user.setPw(encryptedPassword);
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null) ip = req.getRemoteAddr();
            user.setIp(ip);
            User updateUser = userService.userUpdate(user);
            if (updateUser != null) {
                response.setCode("0000");
                response.setMessage("Successed!!");
                response.setData(updateUser);
                Channel channel = new Channel();
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

    @PostMapping(value ="login")
    public ApiResponse userLogin(@RequestBody User user, HttpServletRequest req) throws Exception{
        ApiResponse response = new ApiResponse();
        try{
            User findUser = userService.findByid(user);
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


    @PostMapping(value ="checkId")
    public ApiResponse checkId(@RequestBody User user, HttpServletRequest req) throws Exception{
        ApiResponse response = new ApiResponse();
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
