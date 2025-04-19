package com.wtc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 测试类
 */
@DisplayName("用户信息测试类")
public class UserServiceTest2 {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    /**
     * 测试获取性别 - null
     */
    @Test
    @DisplayName("获取性别-null值")
    public void testGetGender1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getGender(null);
        });
    }

    /**
     * 测试获取性别 - 空字符串
     */
    @Test
    @DisplayName("获取性别-空字符串")
    public void testGetGender2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getGender("");
        });
    }

    @Test
    @DisplayName("获取性别-男性")
    public void testGetGender3() {
        String gender = userService.getGender("100000200010011011");
        Assertions.assertEquals("male", gender, "性别获取错误");
    }

    @Test
    @DisplayName("获取性别-女性")
    public void testGetGender4() {
        String gender = userService.getGender("100000200010011021");
        Assertions.assertEquals("male", gender, "性别获取错误");
    }

    @Test
    @DisplayName("获取年龄-正常身份证")

    public void testGetAge() {
        Integer age = userService.getAge("100000200010011011");
        Assertions.assertEquals(24, age);
    }

    /**
     * 测试获取年龄 - null值
     */
    @Test
    @DisplayName("获取年龄-null值")
    public void testGetAge2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getAge(null);
        });
    }

    /**
     * 测试获取年龄-超长
     */
    @Test
    @DisplayName("获取年龄-长度超长")
    public void testGetAge3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getAge("10000020001001101111");
        });
    }

    /**
     * 测试获取年龄-长度不足
     */
    @Test
    @DisplayName("获取年龄-长度不足")

    public void testGetAge4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getAge("100000200010011");
        });
    }
}
