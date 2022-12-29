package com.suning.jc.service.impl;

import com.suning.jc.mapper.UserMapper;
import com.suning.jc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户信息管理service
 * @author 13120094
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public HashMap<String, Object> login(String userName, String passwd) {
        HashMap<String,Object> res=new HashMap<>();
        HashMap<String,Object> user=userMapper.getUserByUserNm(userName);
        if(user==null){
            res.put("status","400");
            res.put("msg","用户不存在!");
            return res;
        }
        if(MapUtils.getString(user,"passwd","").equals(passwd)){
            res.put("status","200");
            res.put("msg","登录成功!");
            res.put("content",user);
            log.info(userName+"登录成功!");
            return res;
        }else{
            res.put("status","401");
            res.put("msg","密码错误,请重新输入!");
            log.info(userName+"密码错误!");
            return res;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<HashMap<String, Object>> searchUsersByUserName(String userNm) {
        try{
            return userMapper.searchUsersByUserName(userNm);
        } catch (Exception e){
            log.error(e.toString());
            return new ArrayList<>();
        }
    }

    /**
     * 用户自行修改密码
     * @param userNm
     * @param orgPass
     * @param newPass
     * @return
     */
    @Override
    public HashMap<String,String> updatePass(String userNm, String orgPass, String newPass,String operator) {
        HashMap<String,String> res=new HashMap<>();
        HashMap<String,Object> user= userMapper.getUserByUserNm(userNm);
        if(user==null){
            res.put("status","400");
            res.put("msg","用户不存在,或者未登录!");
            return res;
        }else if(MapUtils.getString(user,"passwd","").equals(orgPass)){
            userMapper.updatePass(userNm,newPass,operator);
            res.put("status","200");
            res.put("msg","更新成功!");
            log.info(userNm+"自行修改密码成功!");
            return res;
        }else{
            res.put("status","401");
            res.put("msg","原始密码错误,请重新输入!");
            return res;
        }
    }

    /**
     * 管理员直接修改密码角色
     * @param userNm
     * @param newPass
     * @return
     */
    @Override
    public HashMap<String, String> updateUser(String userNm, String newPass,String role,String operator) {
        HashMap<String,String> res=new HashMap<>();
        userMapper.updateUser(userNm,newPass,role,operator);
        res.put("status","200");
        res.put("msg","更新成功!");
        log.info(userNm+"的密码被"+operator+"更新!");
        return res;
    }

    @Override
    public HashMap<String,String> addUser(String userNm, String passwd,String role,String operator) {
        HashMap<String,String> res=new HashMap<>();
        //检查用户是否重复
        Map<String,Object> user=userMapper.getUserByUserNm(userNm);
        if(user!=null){
            res.put("status","400");
            res.put("msg","用户已存在!不能重复添加");
            return res;
        }
        try {
            userMapper.addUser(userNm, passwd,role, operator);
        }catch (Exception e){
            res.put("status","401");
            res.put("msg","用户添加错误!");
            return res;
        }
        res.put("status","200");
        res.put("msg","添加成功!");
        log.info(operator+"添加用户"+userNm+"成功!");
        return res;
    }

    @Override
    public void delUser(String userId) {
        userMapper.delUser(userId);
        log.info("删除用户"+userId);
    }
}
