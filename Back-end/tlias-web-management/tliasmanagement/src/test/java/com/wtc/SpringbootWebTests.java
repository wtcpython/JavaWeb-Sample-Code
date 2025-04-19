package com.wtc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.wtc.controller.DeptController;
import com.wtc.pojo.Result;
import com.wtc.utils.AliyunOSSOperator;

import cn.hutool.core.io.FileUtil;

@SpringBootTest
public class SpringbootWebTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @Autowired
    private Gson gson;

    @Test
    public void testGson() {
        System.out.println(gson.toJson(Result.success("Hello Gson")));
    }

    @Test
    public void testScope() {
        for (int i = 0; i < 1000; i++) {
            DeptController deptController = applicationContext.getBean(DeptController.class);
            System.out.println(deptController);
        }
    }

    @Test
    public void testUpload() throws Exception {
        byte[] content = FileUtil.readBytes("D:/原神2024.jpg");
        String url = aliyunOSSOperator.upload(content, "原神2024.jpg");
        System.out.println(url);
    }
}
