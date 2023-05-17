package com.video.service.service;

import com.video.service.entity.TokenEntity;
import com.video.service.entity.UserEntity;
import com.video.service.repository.TokenRepository;
import com.video.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public TokenEntity tokenInsert(TokenEntity token) throws Exception{
        return tokenRepository.save(token);
    }
    public TokenEntity findByUserSeq(int userSeq) throws Exception{
        return tokenRepository.findByUserSeq(userSeq);
    }

    @Transactional
    public void updateAccessToken(TokenEntity tokenEntity) throws Exception {
        tokenRepository.updateAccessToken(tokenEntity.getAccessToken(), tokenEntity.getUser().getUserSeq());
    }
}
