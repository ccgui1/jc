package com.suning.jc.controller;

import com.suning.jc.mapper.UserMapper;
import com.suning.jc.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author 13120094
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 默认进入老师登录页
     * @param map
     * @param request
     * @return
     */
    @GetMapping({"","/login","/","index","index.htm","index.html"})
    public String userLoginto(ModelMap map, HttpServletRequest request){
        return "/login";
    }

    /**
     * 学生登录验证
     * @param map
     * @param session
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/userLogin")
    public String login(ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String userNm=request.getParameter("username");
        String passwd=request.getParameter("password");
        HashMap<String,Object> user=userMapper.getUserByUserNm(userNm);
        if(user==null){
            map.put("msg","用户不存在!");
            return "/login";
        }
        if(MapUtils.getString(user,"passwd","").equals(passwd)){
            session.setAttribute("id",MapUtils.getString(user,"user_id",""));
            session.setAttribute("name",MapUtils.getString(user,"user_nm"));
            String role=MapUtils.getString(user,"role");
            session.setAttribute("role",role);
            if("1".equals(role)){
                return "redirect:/user/index";
            }else{
                return "redirect:/student/index";
            }
        }else{
            map.put("msg","密码错误,请重新输入!");
            return "/login";
        }
    }


    @GetMapping("/logout")
    public String userLogout(ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //3.清理session
        session.invalidate();
        return "login";
    }

    @GetMapping("/updatePass")
    public String  toUpdatePass(ModelMap map, HttpServletRequest request){
        return "/updatepass";
    }

    /**
     * 更改当前用户密码
     * @param orgPass
     * @param newPass
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/updatePass")
    public HashMap<String,String> updatePass(String orgPass, String newPass, HttpServletRequest request){
        String userNm=String.valueOf(request.getSession().getAttribute("name"));
        if(StringUtils.isEmpty(userNm)){
            HashMap<String,String> result=new HashMap<>();
            result.put("status","403");
            result.put("msg","请先登录!");
            return result;
        }
        String operator= (String) request.getSession().getAttribute("name");
        //修改密码
        HashMap<String,String> updateRes=userServiceImpl.updatePass(userNm,orgPass,newPass,operator);
        return updateRes;
    }
}