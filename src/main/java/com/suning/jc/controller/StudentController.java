package com.suning.jc.controller;

import com.suning.jc.service.impl.StudentServiceImpl;
import com.suning.jc.utils.CsvUtil;
import com.suning.jc.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 13120094
 */
@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @GetMapping("/index")
    public String  indexStudent(ModelMap map, HttpServletRequest request){
        return "/student";
    }

    @ResponseBody
    @PostMapping("/searchStudents")
    public PageResult searchStudents(@RequestBody HashMap dataMap, HttpServletRequest request){
        PageResult users=studentServiceImpl.searchStudent(dataMap);
        return users;
    }

    /**
     * 下载csv文件 最多10000条
     * @param sstudentNm
     * @param scollege
     * @param sadviser
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/downloadStudents")
    public void downloadStudents(String sstudentNm,String scollege,String sadviser,String suid,String sphone, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String,String> dataMap=new HashMap();
        dataMap.put("studentNm",sstudentNm);
        dataMap.put("college",scollege);
        dataMap.put("adviser",sadviser);
        // 添加身份证和手机号查询
        dataMap.put("uid",suid);
        dataMap.put("phone",sphone);
        List<HashMap<String,Object>> users=studentServiceImpl.downloadStudents(dataMap);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String fileName="建策学员-"+format+".csv";
        String  titles = "编号,姓名,院校,专业,身份证号,手机,邮箱,QQ,课程顾问,报名课程,报名时间,已通过考试,就业单位,备注";
        String[] mapKeys = new String[]{"student_id","student_nm","college","major_nm","uid","phone","email","qq","adviser","sign_course","sign_time","exam_passed","employment_unit","memo"};
        CsvUtil.genCsv(fileName,titles, mapKeys, users, response);
    }

    @ResponseBody
    @RequestMapping("/addStudent")
    public HashMap<String,String> addStudent(@RequestBody HashMap dataMap, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证

        if(StringUtils.isEmpty( MapUtils.getString(dataMap,"studentNm"))||StringUtils.isEmpty( MapUtils.getString(dataMap,"phone"))){
            result.put("status","500");
            result.put("msg","姓名和电话必需输入!");
            return result;
        }
        //检查用户是否已存在  通过姓名+手机号查询
        Integer count=studentServiceImpl.checkUnique(MapUtils.getString(dataMap,"studentNm"),MapUtils.getString(dataMap,"phone"));
        if(count>0){
            result.put("status","500");
            result.put("msg","姓名+手机号已存在,不能重复添加!");
            return result;
        }
        //2 添加用户
        String operator= (String) request.getSession().getAttribute("name");
        dataMap.put("operator",operator);
        studentServiceImpl.addStudent(dataMap);

        result.put("status","200");
        result.put("msg","更新成功!");
        return result;
    }

    /**
     *
     * @param studentId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getStudentClassList")
    public List<HashMap<String,Object>> getStudentClassList(String studentId, HttpServletRequest request){
        return studentServiceImpl.getStudentClassList(studentId);
    }

    @ResponseBody
    @RequestMapping("/delStudent")
    public HashMap<String,String> delStudent(String studentId, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(studentId)){
            result.put("status","500");
            result.put("msg","参数错误!");
            return result;
        }

        studentServiceImpl.delStudent(studentId);
        result.put("status","200");
        result.put("msg","删除成功");
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateStudent")
    public HashMap<String,String> updateStudent(@RequestBody HashMap dataMap, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty( MapUtils.getString(dataMap,"studentId"))||StringUtils.isEmpty( MapUtils.getString(dataMap,"studentNm"))||StringUtils.isEmpty( MapUtils.getString(dataMap,"phone"))){
            result.put("status","500");
            result.put("msg","id姓名和电话必需输入!");
            return result;
        }
        String operator= (String) request.getSession().getAttribute("name");
        dataMap.put("operator",operator);
        studentServiceImpl.updateStudent(dataMap);
        result.put("status","200");
        result.put("msg","更新成功");
        return result;
    }

}