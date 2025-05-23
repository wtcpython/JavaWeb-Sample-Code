package com.wtc.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {
    private List<Object> jobList;
    private List<Object> dataList;
}
