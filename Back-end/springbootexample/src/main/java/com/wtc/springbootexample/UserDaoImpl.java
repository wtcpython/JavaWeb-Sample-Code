package com.wtc.springbootexample;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;

// @Component // 将当前类交给 IOC 管理
@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public List<String> findAll() {
        // 加载并读取 user.txt 文件，获取用户数据
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");
        ArrayList<String> lines = IoUtil.readLines(in, CharsetUtil.CHARSET_UTF_8, new ArrayList<>());
        return lines;
    }

}
