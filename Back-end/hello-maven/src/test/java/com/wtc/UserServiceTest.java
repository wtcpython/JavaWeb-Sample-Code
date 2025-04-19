package com.wtc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 测试类
 */
@DisplayName("用户信息测试类")
public class UserServiceTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("after All");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("before Each");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("after Each");
    }

    @Test
    public void testGetAge() {
        UserService userService = new UserService();
        Integer age = userService.getAge("100000200010011011");
        System.out.println(age);
    }

    @Test
    public void testGetGender() {
        UserService userService = new UserService();
        String gender = userService.getGender("100000200010011011");
        System.out.println(gender);
    }

    @Test
    public void testGetGenderWithAssert() {
        UserService userService = new UserService();
        String gender = userService.getGender("100000200010011011");
        Assertions.assertEquals("male", gender, "性别获取错误");
    }

    @Test
    public void testGetGenderWithAssert2() {
        UserService userService = new UserService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getGender(null);
        });
    }

    /**
     * 参数化测试
     */
    @DisplayName("测试用户性别")
    @ParameterizedTest
    @ValueSource(strings = { "100000200010011011", "100000200010011031", "100000200010011051" })
    public void testGetGender2(String idCard) {
        UserService userService = new UserService();
        String gender = userService.getGender("100000200010011011");
        Assertions.assertEquals("male", gender, "性别获取错误");
    }
}
