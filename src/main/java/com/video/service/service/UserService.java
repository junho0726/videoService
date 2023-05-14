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
    public User userLogin(User user) throws Exception {
        return userRepository.userLogin(user.getId(), user.getPw());
    }

}
