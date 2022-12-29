package com.suning.jc.service;

import com.suning.jc.utils.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface StudentService {
    Integer checkUnique(String studentNm,String phone);
    PageResult searchStudent(HashMap<String,String> para);
    List<HashMap<String,Object>> downloadStudents(HashMap<String, String> para);
    void updateStudent(HashMap<String,String> para);

    void addStudent(HashMap<String,String> para);

    void delStudent(String studentId);

    List<HashMap<String,Object>> getStudentClassList(String studentId);
}
