package com.suning.jc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @author 13120094
 */
@Mapper
public interface DimensionTableMapper {
    @Select("select course_id,course_nm from jc.jc_course")
    List<HashMap<String,Object>> getAllCourse();
}
