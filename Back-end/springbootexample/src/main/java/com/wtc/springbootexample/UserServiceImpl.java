package com.wtc.springbootexample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Component // 将当前类交给 IOC 管理
@Service
public class UserServiceImpl implements UserService {

    @Autowired // 依赖注入
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        // 获取数据
        List<String> lines = userDao.findAll();

        // 解析用户信息，封装为 User 对象 -> list 集合
        List<User> userList = lines.stream().map(line -> {
            String[] split = line.split(",");
            Integer id = Integer.valueOf(split[0]);
            String username = split[1];
            String password = split[2];
            String name = split[3];
            Integer age = Integer.valueOf(split[4]);
            LocalDateTime updateTime = LocalDateTime.parse(split[5],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new User(id, username, password, name, age, updateTime);
        }).toList();

        return userList;
    }

}
