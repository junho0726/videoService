package com.video.service.controller;

import com.video.service.entity.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class FileController {

    @PostMapping(value = "insert/file")
    public FileEntity insertFile(@RequestBody FileEntity file) {
        return file;
    }

}
