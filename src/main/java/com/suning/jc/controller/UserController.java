package com.suning.jc.controller;

import com.suning.jc.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author 13120094
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 用户信息管理首页
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/index")
    public String  indexUser(ModelMap map, HttpServletRequest request){
        return "/user";
    }

    /**
     * 检索用户信息
     * @param userNm
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/searchUsers")
    public List<HashMap<String, Object>> searchUsers(String userNm, HttpServletRequest request){
        List<HashMap<String, Object>> users=userServiceImpl.searchUsersByUserName(userNm);
        return users;
    }



    @ResponseBody
    @RequestMapping("/addUser")
    public HashMap<String,String> addUser(String userNm,String passwd, String role,HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(userNm)||StringUtils.isEmpty(passwd)){
            result.put("status","500");
            result.put("msg","参数错误!");
            return result;
        }
        //2 添加用户
        String operator= (String) request.getSession().getAttribute("name");
        result =userServiceImpl.addUser(userNm, passwd,role,operator);
        return result;
    }

    /**
     * 删除用户
     * @param userId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public HashMap<String,String> delUser(String userId, HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(userId)){
            result.put("status","500");
            result.put("msg","参数错误!");
            return result;
        }
        if("1".equals(userId)){
            result.put("status","500");
            result.put("msg","管理员账号不能删除!");
            return result;
        }
        userServiceImpl.delUser(userId);
        result.put("status","200");
        result.put("msg","删除成功");
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateUser")
    public HashMap<String,String> updateUer(String userNm,String passwd, String role,HttpServletRequest request){
        HashMap<String,String> result=new HashMap<>();
        //1 参数验证
        if(StringUtils.isEmpty(userNm)||StringUtils.isEmpty(passwd)){
            result.put("status","500");
            result.put("msg","参数错误!");
            return result;
        }
        String operator= (String) request.getSession().getAttribute("name");
        return userServiceImpl.updateUser(userNm, passwd,role,operator);
    }

}