package com.wtc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wtc.pojo.Dept;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门信息
     * @return
     */
    // 手动结果映射
    // @Results({
    //     @Result(column = "create_time", property = "createTime"),
    //     @Result(column = "update_time", property = "updateTime")
    // })
    // 方式二：起别名
    // @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc;")
    @Select("select id, name, create_time, update_time from dept order by update_time desc;")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    int deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    int insert(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
