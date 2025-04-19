package com.wtc.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.wtc.pojo.Emp;
import com.wtc.pojo.EmpQueryParam;
import com.wtc.pojo.LoginInfo;
import com.wtc.pojo.PageResult;

import io.jsonwebtoken.security.InvalidKeyException;

public interface EmpService {

    // /**
    //  * 分页查询
    //  * @param page 页码
    //  * @param pageSize 每页条数
    //  * @param name 员工姓名
    //  * @param gender 员工性别
    //  * @return
    //  */
    // PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 分页查询
     * @param empQueryParam
     * @return
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 批量删除员工
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 根据 ID 查询员工信息
     * @param id
     * @return
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工信息
     * @param emp
     */
    void update(Emp emp);

    /**
     * 员工登录
     * @param emp
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    LoginInfo login(Emp emp) throws InvalidKeyException, NoSuchAlgorithmException;

}
