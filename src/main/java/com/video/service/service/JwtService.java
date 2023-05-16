package com.video.service.service;


import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.UserEntity;
import com.video.service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    private static final String SECRET_KEY = "videoService";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    public String createToken(String subject, String type) {

        // 토큰을 서명하기 위해 사용해야할 알고리즘 선택
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowTime = System.currentTimeMillis();
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        if(type.equals("Access")){
            return Jwts.builder()
                    .setSubject(subject)
                    .signWith(signatureAlgorithm, signingKey)
                    .setExpiration(new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME))
                    .compact();
        }else{
            return Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(nowTime + REFRESH_TOKEN_EXPIRE_TIME))
                    .signWith(signatureAlgorithm, signingKey)
                    .compact();
        }
    }

    public Map getSubject(String token) {
        Map resultMap = new HashMap();
        String result = "";
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();

            String[] tokenTemp = claims.getSubject().split(":");
            resultMap.put("fdId",tokenTemp[0]);
            resultMap.put("result","S");
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            System.out.println(e);
            resultMap.put("result","Expire");
        } catch (Exception e) { // 그외 에러났을 경우
            System.out.println(e);
            resultMap.put("result","Err");
        }
        return resultMap;

    }

}
