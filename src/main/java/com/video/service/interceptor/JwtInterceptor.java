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

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken != null) {
            boolean isAccessToken = accessTokenValidation(accessToken);
            if (isAccessToken) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public Boolean accessTokenValidation(String accessToken) throws Exception {

        Map accessTokenResult = jwtService.getSubject(accessToken);

        if(!accessTokenResult.get("result").equals("S")){
            return false;
        }
        UserEntity accessUser = new UserEntity();
        accessUser.setId(accessTokenResult.get("fdId").toString());
        UserEntity user = userService.findByid(accessUser);

        if (user == null || user.getToken() == null) {
            return false;
        }

        return accessToken.equals(user.getToken().getAccessToken());
    }


}

