package com.suning.jc.controller;

import com.suning.jc.service.impl.ClassServiceImpl;
import com.suning.jc.service.impl.DimensionTableServiceImpl;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author 13120094
 */
@Slf4j
@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private DimensionTableServiceImpl dimensionTableServiceImpl;
    @Autowired
    private ClassServiceImpl classServiceImpl;

    @RequestMapping("/index")
    public String  classIndex(ModelMap map, HttpServletRequest request){
        HashMap<String, Object> courseMap=dimensionTableServiceImpl.getAllCourse();
        map.put("courseMap",courseMap);
        return "/class";
    }
    
    @ResponseBody
    @PostMapping("/searchClass")
    public PageResult searchClass(@RequestBody HashMap dataMap, HttpServletRequest request){
        PageResult teachers=classServiceImpl.searchClass(dataMap);
        return teachers;
    }

    @ResponseBody
    @RequestMapping("/addClass")
    public HashMap<String,String> addClass(@RequestBody HashMap dataMap,HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(dataMap.get("courseNm"))||StringUtils.isEmpty(dataMap.get("classType"))||StringUtils.isEmpty(dataMap.get("teacherNm"))){
            result.put("status","500");
            result.put("msg","参数错误!");
        }
        //取当前用户
        String operator= (String) request.getSession().getAttribute("name");
        dataMap.put("operator",operator);
        classServiceImpl.addClass(dataMap);

        result.put("status","200");
        result.put("msg","添加成功");
        return result;
    }


    @ResponseBody
    @RequestMapping("/delClass")
    public HashMap<String,String> delClass(String classId, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(classId)){
            result.put("status","500");
            result.put("msg","参数错误!");
        }
        classServiceImpl.delClass(classId);
        result.put("status","200");
        result.put("msg","删除成功");
        return result;
    }
    @ResponseBody
    @RequestMapping("/updateClass")
    public HashMap<String,Object> updateClass(@RequestBody HashMap dataMap, HttpServletRequest request){
        HashMap<String,Object> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(dataMap.get("classId"))||StringUtils.isEmpty(dataMap.get("courseNm"))||StringUtils.isEmpty(dataMap.get("classType"))||StringUtils.isEmpty(dataMap.get("teacherNm"))){
            result.put("status","500");
            result.put("msg","参数错误!");
        }
        //
        String operator= (String) request.getSession().getAttribute("name");
        dataMap.put("operator",operator);
        classServiceImpl.updateClass(dataMap);
        result.put("status", "200");
        result.put("msg", "更新成功");
        return result;
    }

}