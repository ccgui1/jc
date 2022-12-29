package com.suning.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suning.jc.mapper.ClaStuMapper;
import com.suning.jc.service.ClaStuService;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author 13120094
 */
@Slf4j
@Service
public class ClaStuServiceImpl implements ClaStuService {
    @Autowired
    private ClaStuMapper claStuMapper;
    @Transactional(readOnly = true)
    @Override
    public PageResult searchClsStuByClass(HashMap<String, String> para) {
        int pageNum = MapUtils.getIntValue(para,"pageNum",1);
        int pageSize = MapUtils.getIntValue(para,"pageSize",10);
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap<String,Object>> clastus = claStuMapper.searchClsStuByClass(para);
        PageInfo<HashMap<String,Object>> pageInfo=new PageInfo<>(clastus);
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
        log.info("下载班级"+MapUtils.getString(para,"classId")+"学生成功!");
        return claStuMapper.downloadClsStuByClass(para);
    }

    /**
     * 弹出框使用
     * @param para
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageResult searchStudentForClass(HashMap<String, Object> para) {
        int pageNum = MapUtils.getIntValue(para,"pageNum",1);
        int pageSize = MapUtils.getIntValue(para,"pageSize",10);
        Integer count=claStuMapper.searchStudentCountForClass(para);
        para.put("startRow",(pageNum-1)*pageSize);
        List<HashMap<String,Object>> clastu = claStuMapper.searchStudentForClass(para);

        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalSize(count);
        pageResult.setTotalPages(count/pageSize+1);
        pageResult.setContent(clastu);
        return pageResult;
    }

    @Override
    public void delClassStudent(String classId, String studentId) {
        log.info("删除学生 班级:"+classId+" 学号:"+studentId);
        claStuMapper.delClaStu(classId,studentId);
    }

    @Override
    public void batchAddStudent(List studentList) {
        log.info("批量添加学生"+studentList.size());
        claStuMapper.batchAddStudent(studentList);
    }


}
