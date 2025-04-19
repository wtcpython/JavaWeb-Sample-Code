package com.wtc.service;

import java.util.List;
import java.util.Map;

import com.wtc.pojo.JobOption;

public interface ReportService {

    /**
     * 统计员工职位人数
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别人数
     * @return
     */
    List<Map<String, Object>> getEmpGenderData();

}
