package com.suning.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suning.jc.mapper.StudentMapper;
import com.suning.jc.service.StudentService;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 用户管理service
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Transactional(readOnly = true)
    @Override
    public Integer checkUnique(String studentNm, String phone){
        return studentMapper.checkUnique(studentNm,phone);
    }
    @Transactional(readOnly = true)
    @Override
    public PageResult searchStudent(HashMap<String, String> para) {
        int pageNum = MapUtils.getIntValue(para,"pageNum",1);
        int pageSize = MapUtils.getIntValue(para,"pageSize",10);
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap<String,Object>> students = studentMapper.searchStudent(para);
        PageInfo<HashMap<String,Object>> pageInfo=new PageInfo<>(students);
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

    @Transactional(readOnly = true)
    @Override
    public List<HashMap<String,Object>> downloadStudents(HashMap<String, String> para) {
        List<HashMap<String,Object>> students = studentMapper.downloadSearchStudent(para);
        log.info("下载学生信息成功!");
        return students;
    }

    @Override
    public void updateStudent(HashMap<String, String> para) {
        log.info("更新学生信息:"+para);
        studentMapper.updateStudent(para);
    }

    @Override
    public void addStudent(HashMap<String, String> para) {
        log.info("添加学生信息:"+para);
        studentMapper.addStudent(para);
    }

    @Override
    public void delStudent(String studentId) {
        log.info("删除学生:"+studentId);
        studentMapper.delStudent(studentId);
    }

    @Override
    public List<HashMap<String,Object>> getStudentClassList(String studentId){
        return studentMapper.getStudentClassList(studentId);
    }
}
