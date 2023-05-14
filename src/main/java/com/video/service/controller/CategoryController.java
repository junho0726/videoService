package com.video.service.controller;

import com.video.service.entity.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    @PostMapping(value = "insert/category")
    public Category insertCategory(@RequestBody Category category) {
        return category;
    }

}
