package com.wtc.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wtc.pojo.Emp;
import com.wtc.pojo.LoginInfo;
import com.wtc.pojo.Result;
import com.wtc.service.EmpService;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求 Controller
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) throws InvalidKeyException, NoSuchAlgorithmException {
        log.info("登录请求：{}", emp);
        LoginInfo info = empService.login(emp);
        if (info != null) {
            return Result.success(info);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
