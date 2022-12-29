package com.suning.jc.service;


import com.suning.jc.utils.PageResult;

import java.util.HashMap;

public interface ClassService {
    HashMap<String,Object> getClassById(String classId);
    PageResult searchClass(HashMap<String,String> para);
    void addClass(HashMap<String,String> para);
    void updateClass(HashMap<String,String> para);
    void delClass(String classId);
}
