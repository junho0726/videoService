package com.video.service.service;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.KeywordEntity;
import com.video.service.repository.ChannelRepository;
import com.video.service.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public List<KeywordEntity> findByKeyword(int categorySeq){

        return keywordRepository.findByCategorySeq(categorySeq);
    }


}
