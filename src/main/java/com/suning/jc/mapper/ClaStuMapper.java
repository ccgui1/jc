package com.suning.jc.mapper;

import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author 13120094
 */
@Mapper
public interface ClaStuMapper {

    List<HashMap<String,Object>>  searchClsStuByClass(HashMap  dataMap);

    List<HashMap<String,Object>>  downloadClsStuByClass(HashMap  dataMap);

    @Delete("delete from jc.jc_class_student where class_id=#{classId} and student_id=#{studentId}")
    void delClaStu(@Param("classId")String classId,@Param("studentId")String studentId);

    List<HashMap<String,Object>>  searchStudentForClass(HashMap  dataMap);

    Integer searchStudentCountForClass(HashMap  dataMap);

    void batchAddStudent(List list);
}
