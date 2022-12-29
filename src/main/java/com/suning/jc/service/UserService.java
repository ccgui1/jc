package com.suning.jc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {


    Map<String,Object> login(String userName,String passwd);
    List<HashMap<String,Object>> searchUsersByUserName(String userNm);

    Map<String,String> updatePass(String userNm, String orgPass, String newPass,String operator);

    Map<String,String> updateUser(String userNm, String newPass,String role,String operator);

    HashMap<String,String>  addUser(String userNm, String passwd,String role,String operator);

    void delUser(String userId);
}
