package com.wtc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.AliyunOSSOperator;

@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public String upload(MultipartFile image) throws Exception {
        String url = aliyunOSSOperator.upload(image.getBytes(), image.getOriginalFilename());
        System.out.println(url);
        return url;
    }
}
