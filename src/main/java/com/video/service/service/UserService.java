package com.video.service.service;

import com.video.service.entity.UserEntity;
import com.video.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity userJoin(UserEntity user) throws Exception{
        return userRepository.save(user);
    }
    public UserEntity findByid(UserEntity user) throws Exception {
        return userRepository.findByid(user.getId());
    }

    @Transactional
    public UserEntity userUpdate(UserEntity user) throws Exception {
        UserEntity userEntity = userRepository.findById(user.getUserSeq()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 회원입니다.");
        });
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setTel(user.getTel());

        return userEntity;
    }

    public boolean checkId(UserEntity user) throws Exception{
       UserEntity existingUser =  userRepository.findByid(user.getId());

        if (existingUser != null) {
            return false;
        } else {
            return true;
        }
    }

}
