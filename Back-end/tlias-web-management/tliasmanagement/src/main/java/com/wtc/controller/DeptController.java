package com.wtc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wtc.anno.Log;
import com.wtc.pojo.Dept;
import com.wtc.pojo.Result;
import com.wtc.service.DeptService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

// @Scope("prototype") // 多例
// 单例的 bean 是线程安全的，多例的 bean 存在线程安全问题
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        List<Dept> depts = deptService.findAll();
        return Result.success(depts);
    }

    @Log
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        log.info("删除部门数据：{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门数据：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("查询部门数据：{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门数据：{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
