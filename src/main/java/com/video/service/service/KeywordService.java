package com.video.service.service;

import com.video.service.entity.KeywordEntity;
import com.video.service.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public List<KeywordEntity> findByKeyword(int categorySeq){

        return keywordRepository.findByCategorySeq(categorySeq);
    }


}
