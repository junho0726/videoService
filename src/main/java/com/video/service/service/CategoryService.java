package com.video.service.service;

import com.video.service.entity.CategoryEntity;
import com.video.service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryEntity> findAll (){
        List<CategoryEntity> categoryList = categoryRepository.findAll();

        return categoryList;
    }
}
