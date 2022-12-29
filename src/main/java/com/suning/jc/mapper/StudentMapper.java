package com.suning.jc.mapper;

import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select count(1) from jc.jc_student where student_nm=#{studentNm} and phone=#{phone}")
    Integer checkUnique(@Param("studentNm")String studentNm,@Param("phone")String phone);

    List<HashMap<String,Object>> searchStudent(HashMap<String,String> para);

    List<HashMap<String,Object>> downloadSearchStudent(HashMap<String,String> para);

    void updateStudent(HashMap<String,String> para);

    void addStudent(HashMap<String,String> para);

    @Delete("delete from jc.jc_student where student_id=#{studentId}")
    void delStudent(@Param("studentId")String studentId);

    List<HashMap<String,Object>> getStudentClassList(String studentId);


}
