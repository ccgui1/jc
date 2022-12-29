package com.suning.jc.service.impl;


import com.suning.jc.mapper.DimensionTableMapper;
import com.suning.jc.service.DimensionTableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 获取维表信息
 */
@Slf4j
@Service
public class DimensionTableServiceImpl implements DimensionTableService {
    @Autowired
    private DimensionTableMapper dimensionTableMapper;
    @Transactional(readOnly = true)
    @Override
    public HashMap<String, Object> getAllCourse() {
        List<HashMap<String,Object>> c=dimensionTableMapper.getAllCourse();
        HashMap<String,Object> tmp=new HashMap<>();
        for(HashMap<String,Object> t:c){
            tmp.put(MapUtils.getString(t,"course_id",""),MapUtils.getString(t,"course_nm",""));
        }
        return tmp;
    }


}
