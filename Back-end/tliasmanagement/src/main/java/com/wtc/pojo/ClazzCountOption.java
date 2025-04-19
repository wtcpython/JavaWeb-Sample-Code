package com.wtc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 班级人数统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List<Object> clazzList; //职位列表
    private List<Object> dataList; //人数列表
}
