package com.suning.jc.controller;


import com.suning.jc.service.impl.ClassServiceImpl;
import com.suning.jc.utils.CsvUtil;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 13120094
 */
@Slf4j
@Controller
@RequestMapping("/clastu")
public class ClaStuController {
    @Autowired
    private ClassServiceImpl classServiceImpl;
    @Autowired
    private com.suning.jc.service.impl.ClaStuServiceImpl claStuServiceImpl;

    @GetMapping("/index/{classid}")
    public String  indexCourseNew(@PathVariable String classid, ModelMap map, HttpServletRequest request){
        if(StringUtils.isEmpty(classid)){
            return "/class";
        }
        HashMap<String, Object> classRow=classServiceImpl.getClassById(classid);
        if(classRow==null||classRow.isEmpty()){
            return "class";
        }
        map.put("classRow",classRow);
        return "/clastu";
    }

    /**
     * 检索班级里的学生
     * @param dataMap
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/searchClaStu")
    public PageResult searchClaStu(@RequestBody HashMap dataMap,  HttpServletRequest request){
        PageResult students=claStuServiceImpl.searchClsStuByClass(dataMap);
        return students;
    }

    @PostMapping("/downloadClsStu")
    public void downloadClsStu(String classId,HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String,String> dataMap=new HashMap<>();
        dataMap.put("classId",classId);
        HashMap<String, Object> classRow=classServiceImpl.getClassById(classId);
        String fileName=MapUtils.getString(classRow,"class_id")+"-"
                +MapUtils.getString(classRow,"class_type")+"-"
                +MapUtils.getString(classRow,"date_range")+"-"
                +MapUtils.getString(classRow,"teacher_nm")+".csv";
        String  titles = "编号,姓名,院校,手机,报名课程";
        String[] mapKeys = new String[]{"student_id","student_nm","college","phone","sign_course"};
        List<HashMap<String,Object>> students=claStuServiceImpl.downloadStudents(dataMap);
        CsvUtil.genCsv(fileName,titles, mapKeys, students, response);
    }


    /**
     * 学员检索弹出框
     * @param dataMap
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/searchStudentForClass")
    public PageResult searchStudentForClass(@RequestBody HashMap dataMap,  HttpServletRequest request){
        PageResult students=claStuServiceImpl.searchStudentForClass(dataMap);
        return students;
    }

    @ResponseBody
    @PostMapping("/delClaStu")
    public HashMap<String,Object> delClaStu(String classId,String studentId, HttpServletRequest request){
        HashMap<String,Object> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(classId)||StringUtils.isEmpty(studentId)){
            result.put("status","500");
            result.put("msg","参数错误!");
        }
        claStuServiceImpl.delClassStudent(classId,studentId);
        result.put("status","200");
        result.put("msg","删除成功");
        return result;
    }


    @ResponseBody
    @RequestMapping("/batchAddStudent")
    public HashMap<String,String> batchAddStudent(@RequestBody HashMap dataMap, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        String classId= MapUtils.getString(dataMap,"classId","");
        String courseNm= MapUtils.getString(dataMap,"courseNm","");
        List students= (List) MapUtils.getObject(dataMap, "students");
        String operator= (String) request.getSession().getAttribute("name");

        List<Map<String,String>> resStudents=new ArrayList<>();
        for(int i=0;i<students.size();i++){
            Map s= (Map) students.get(i);
            Map<String,String> row=new HashMap<>();
            row.put("classId",classId);
            row.put("courseNm",courseNm);
            row.put("studentId",MapUtils.getString(s,"student_id"));
            row.put("studentNm",MapUtils.getString(s,"student_nm"));
            row.put("college",MapUtils.getString(s,"college"));
            row.put("phone",MapUtils.getString(s,"phone"));
            row.put("operator",operator);
            resStudents.add(row);
        }
        claStuServiceImpl.batchAddStudent(resStudents);
        result.put("status","200");
        result.put("msg","批量添加成功");
        return result;
    }

}