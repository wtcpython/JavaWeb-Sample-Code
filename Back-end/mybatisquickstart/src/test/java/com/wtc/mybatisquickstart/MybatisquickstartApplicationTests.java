package com.wtc.mybatisquickstart;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisquickstartApplicationTests {




	

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testFindAll() {
		List<User> userList = userMapper.findAll();
		userList.forEach(System.out::println);
	}

	@Test
	public void testDeleteById() {
		Integer lines = userMapper.deleteById(5);
		System.out.println("受影响的行数：" + lines);
	}

	@Test
	public void testInsert() {
		User user = new User(
				null,
				"gaoyuanyuan",
				"666888",
				"高圆圆",
				18);
		userMapper.insert(user);
	}

	@Test
	public void testUpdate() {
		User user = new User(
				1,
				"zhouyu",
				"666888",
				"周瑜",
				20);
		userMapper.update(user);
	}

	@Test
	public void testFindByNameAndPassword() {
		User user = userMapper.findByNameAndPassword("zhouyu", "666888");
		System.out.println(user);
	}
}
