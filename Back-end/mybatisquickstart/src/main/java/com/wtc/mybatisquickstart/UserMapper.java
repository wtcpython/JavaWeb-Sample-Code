package com.wtc.mybatisquickstart;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     * 
     * @return List
     */
    // @Select("select * from user")
    public List<User> findAll();

    /**
     * 根据id删除用户
     * 
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    public Integer deleteById(Integer id);

    /**
     * 插入用户
     * 
     * @param user
     */
    @Insert("insert into user(username, password, name, age) values(#{username}, #{password}, #{name}, #{age})")
    public void insert(User user);

    /**
     * 更新用户
     * 
     * @param user
     */
    @Update("update user set username = #{username}, password = #{password}, name = #{name}, age = #{age} where id = #{id}")
    public void update(User user);

    /**
     * 根据用户名和密码查询用户
     * 
     * @param username
     * @param password
     * @return User
     */
    @Select("select * from user where username=#{username} and password=#{password}")
    public User findByNameAndPassword(@Param("username") String username, @Param("password") String password);
}
