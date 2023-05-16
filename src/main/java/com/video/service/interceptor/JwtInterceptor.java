package com.video.service.interceptor;

import com.video.service.entity.UserEntity;
import com.video.service.service.JwtService;
import com.video.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("start");
        String accessToken = request.getHeader(ACCESS_TOKEN);
        String refreshToken = request.getHeader(REFRESH_TOKEN);
        Map accessTokenResult = new HashMap();
        if (accessToken != null) {
             accessTokenResult = jwtService.getSubject(accessToken);
            if (accessTokenResult.get("result").equals("S")) {
            }
        } else if (refreshToken != null) {
            boolean isRefreshToken = refreshTokenValidation(refreshToken);
            String loginId =  accessTokenResult.get("fdId").toString();
            if (isRefreshToken) {
                String newAccessToken = jwtService.createToken(loginId, "Access");
            } else {
                String newRefreshToken = jwtService.createToken(loginId, "Refresh");
            }
        } else {
            return false;
        }
        return true;
    }

    public Boolean refreshTokenValidation(String refreshToken) throws Exception {

        Map refreshTokenResult = jwtService.getSubject(refreshToken);

        if(!refreshTokenResult.get("result").equals("S")){
            return false;
        }
        UserEntity refreshUser = new UserEntity();
        refreshUser.setId(refreshTokenResult.get("fdId").toString());
        UserEntity user = userService.findByid(refreshUser);

        return refreshToken.equals(user.getRefreshToken());
    }


}

