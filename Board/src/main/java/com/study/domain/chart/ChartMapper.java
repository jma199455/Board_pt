package com.study.domain.chart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChartMapper {
    
    public List<StatisticsGenderBarVO> getListLine(@Param("param") ChartRequestVO request) throws Exception;
}
