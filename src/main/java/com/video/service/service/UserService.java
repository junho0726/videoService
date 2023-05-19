package com.video.service.service;

import com.video.service.entity.UserEntity;
import com.video.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserEntity userUpdate(UserEntity user) throws Exception {
        Optional<UserEntity> updateUser = userRepository.findById(user.getUserSeq());
        if (updateUser.isPresent()) {
            UserEntity existingUser = updateUser.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setTel(user.getTel());
            existingUser.setPw(user.getPw());
            return userRepository.save(existingUser);
        } else {
            UserEntity findUser = userRepository.findByid(user.getId());
            if (findUser != null) {
                findUser.setEmail(user.getEmail());
                findUser.setName(user.getName());
                findUser.setTel(user.getTel());
                findUser.setPw(user.getPw());
                return userRepository.save(findUser);
            } else {
                throw new Exception("User not found");
            }
        }
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
