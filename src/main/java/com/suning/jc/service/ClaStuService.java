package com.suning.jc.service;


import com.suning.jc.utils.PageResult;

import java.util.HashMap;
import java.util.List;

public interface ClaStuService {
    PageResult  searchClsStuByClass(HashMap<String, String> dataMap);
    List<HashMap<String,Object>> downloadStudents(HashMap<String, String> para);
    PageResult  searchStudentForClass(HashMap<String, Object> dataMap);

    void delClassStudent(String classId,String studentId);
    void batchAddStudent(List list);
}
