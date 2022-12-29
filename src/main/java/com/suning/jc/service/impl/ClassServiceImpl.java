package com.suning.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suning.jc.mapper.ClassMapper;
import com.suning.jc.service.ClassService;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
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
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper;
    @Transactional(readOnly = true)
    @Override
    public HashMap<String, Object> getClassById(String classId) {
        return classMapper.getClassById(classId);
    }
    @Transactional(readOnly = true)
    @Override
    public PageResult searchClass(HashMap<String, String> para) {
        int pageNum = MapUtils.getIntValue(para,"pageNum",1);
        int pageSize = MapUtils.getIntValue(para,"pageSize",10);
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap<String,Object>> registrations = classMapper.searchClass(para);
        PageInfo<HashMap<String,Object>> pageInfo=new PageInfo<>(registrations);
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

    @Override
    public void addClass(HashMap<String, String> para) {
        log.info("创建班级:"+para);
        classMapper.addClass( para);
    }

    @Override
    public void updateClass(HashMap<String, String> para) {
        log.info("更新班级:"+para);
        classMapper.updateClass(para);
    }

    @Override
    public void delClass(String classId) {
        log.info("删除班级:"+classId);
        classMapper.delClass(classId);
    }
}
