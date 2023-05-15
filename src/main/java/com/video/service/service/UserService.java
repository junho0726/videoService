package com.video.service.service;

import com.video.service.entity.User;
import com.video.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User userJoin(User user) throws Exception{
        return userRepository.save(user);
    }
    public User findByid(User user) throws Exception {
        return userRepository.findByid(user.getId());
    }

    public User userUpdate(User user) throws Exception {
        Optional<User> updateUser = userRepository.findById(user.getUserSeq());
        if (updateUser.isPresent()) {
            User existingUser = updateUser.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setTel(user.getTel());
            existingUser.setPw(user.getPw());
            return userRepository.save(existingUser);
        } else {
            User findUser = userRepository.findByid(user.getId());
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

    public boolean checkId(User user) throws Exception{
       User existingUser =  userRepository.findByid(user.getId());

        if (existingUser != null) {
            return false;
        } else {
            return true;
        }
    }

}
