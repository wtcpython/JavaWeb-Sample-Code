package com.wtc.springbootexample;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息 Controller
 * RestController = Controller + ResponseBody
 */
@RestController
public class UserController {

    @Autowired // 依赖注入
    private UserService userService; 
    
    @RequestMapping("/list")
    public List<User> list() throws FileNotFoundException {
        // 获取数据
        List<User> userList = userService.findAll();

        // 返回数据 - Json 格式
        return userList;
    }
}
