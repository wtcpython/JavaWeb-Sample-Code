package com.wtc.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wtc.mapper.EmpExprMapper;
import com.wtc.mapper.EmpMapper;
import com.wtc.pojo.Emp;
import com.wtc.pojo.EmpExpr;
import com.wtc.pojo.EmpLog;
import com.wtc.pojo.EmpQueryParam;
import com.wtc.pojo.LoginInfo;
import com.wtc.pojo.PageResult;
import com.wtc.service.EmpLogService;
import com.wtc.service.EmpService;
import com.wtc.utils.JwtUtils;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        List<Emp> rows = empMapper.list(empQueryParam);

        try (Page<Emp> p = (Page<Emp>) rows) {
            return new PageResult<Emp>(p.getTotal(), p.getResult());
        }
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public void save(Emp emp) {
        // 1. 保存员工的基本信息
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            // 2. 保存员工的工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(expr -> {
                    expr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public void delete(List<Integer> ids) {
        // 删除员工的基本信息
        empMapper.deleteByIds(ids);

        // 删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public void update(Emp emp) {
        // 根据 ID 修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 根据 ID 修改员工的工作经历信息
        // 先删除
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        // 再添加
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) throws InvalidKeyException, NoSuchAlgorithmException {
        // 调用 mapper 接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        // 判断是否存在用户，若存在，组装成功信息
        if (e != null) {
            log.info("登录成功，员工信息：{}", e);

            // 生成 JWT 令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateToken(claims);

            LoginInfo loginInfo = new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
            return loginInfo;
        }

        // 不存在返回 null
        return null;
    }

}
