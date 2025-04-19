package com.wtc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wtc.pojo.Result;
import com.wtc.utils.AliyunOSSOperator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator ossOperator;

    // /**
    // * 本地存储方案
    // * @param name
    // * @param age
    // * @param file
    // * @return
    // */
    // @PostMapping("/upload")
    // public Result upload(String name, Integer age, MultipartFile file) {
    // log.info("name: {},age: {},file: {}", name, age, file);
    // // 保存文件

    // String originalFileName = file.getOriginalFilename();
    // String extension =
    // originalFileName.substring(originalFileName.lastIndexOf("."));
    // String newFileName = UUID.randomUUID().toString().replace("-", "") +
    // extension;

    // try {
    // file.transferTo(new File("D:/" + newFileName));
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // return Result.success();
    // }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("file: {}", file.getOriginalFilename());

        // 将文件上传至 Aliyun OSS
        String url = ossOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传 OSS，url: {}", url);

        return Result.success(url);
    }
}
