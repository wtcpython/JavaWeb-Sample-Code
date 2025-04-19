package com.wtc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wtc.pojo.EmpExpr;

@Mapper
public interface EmpExprMapper {

    /**
     * 批量保存员工工作经历
     * @param exprList
     */
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工 ID 批量删除员工工作经历
     * @param ids
     */
    void deleteByEmpIds(List<Integer> ids);
}
