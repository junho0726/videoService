package com.video.service.controller;

import com.video.service.dto.ApiResponseDto;
import com.video.service.entity.*;
import com.video.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "category/findAll")
    public ApiResponseDto categoryFindAll() throws Exception {
        ApiResponseDto response = new ApiResponseDto();
        try {
            List<CategoryEntity> categoryEntityList = categoryService.findAll();

            response.setData(categoryEntityList);
            response.setCode("0000");
            response.setMessage("Successed!!");
        }catch (Exception e){
            e.printStackTrace();
            response.setCode("0001");
            response.setMessage("Error: " + e.getMessage());
        }
            return response;
    }

}
