package com.suning.jc.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select class_id,course_nm,class_type,date_range,date_detail,teacher_nm,headmaster,date_format(create_time,'%Y-%m-%d %T') as create_time from jc.jc_class where class_id=#{classId} limit 1")
    HashMap<String,Object> getClassById(@Param("classId")String classId);
    List<HashMap<String,Object>>  searchClass(HashMap<String,String> para);
    void addClass(HashMap<String,String> para);
    void updateClass(HashMap<String,String> para);
    @Delete("delete from jc.jc_class where class_id=#{classId}")
    void delClass(String classId);


}
