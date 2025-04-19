package com.wtc.service;

import java.util.List;

import com.wtc.pojo.Dept;

public interface DeptService {

    /**
     * 查询所有部门信息
     * @return
     */
    List<Dept> findAll();

    int deleteById(Integer id);

    int add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
