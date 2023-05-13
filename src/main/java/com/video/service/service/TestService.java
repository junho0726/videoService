package com.video.service.service;

import com.video.service.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service는 MyBatis와 동일
*/
@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
}
