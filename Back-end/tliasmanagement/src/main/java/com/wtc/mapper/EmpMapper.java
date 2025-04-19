package com.wtc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.wtc.pojo.Emp;
import com.wtc.pojo.EmpQueryParam;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {

    // 原始查询方法
    // /**
    // * 查询员工总数
    // *
    // * @return 员工总数
    // */
    // @Select("select count(*) from emp left join dept d on emp.dept_id = d.id")
    // public Long count();

    // /**
    // * 分页查询
    // *
    // * @return
    // */
    // @Select("select emp.*, d.name deptName from emp left join dept d on
    // emp.dept_id = d.id order by emp.update_time desc limit #{start},
    // #{pageSize};")
    // public List<Emp> list(Integer start, Integer pageSize);

    // PageHelper 方法
    // @Select("select emp.*, d.name deptName from emp left join dept d on
    // emp.dept_id = d.id order by emp.update_time desc")
    // public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate
    // end);

    /**
     * 条件查询员工信息
     * 
     * @param empQueryParam
     * @return
     */
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     * 
     * @param emp
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insert(Emp emp);

    /**
     * 根据 ID 批量删除员工的基本信息
     * @param ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 根据 ID 查询员工基本信息以及员工工作经历
     * @param id
     * @return
     */
    public Emp getById(Integer id);

    /**
     * 根据 ID 更新员工的基本信息
     * @param emp
     */
    public void updateById(Emp emp);

    /**
     * 统计员工职位人数
     * @return
     */
    public List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     * @return
     */
    public List<Map<String, Object>> countEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     * @param emp
     * @return
     */
    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    public Emp selectByUsernameAndPassword(Emp emp);
}
