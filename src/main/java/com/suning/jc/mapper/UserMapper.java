package com.suning.jc.mapper;

import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author 13120094
 */
@Mapper
public interface UserMapper {

    @Select("select user_id,user_nm,passwd,role from jc.jc_user where user_nm=#{userNm} limit 1")
    HashMap<String,Object> getUserByUserNm(@Param("userNm")String userNm);

    List<HashMap<String,Object>> searchUsersByUserName(@Param("userNm")String userNm);

    @Update("update jc.jc_user set passwd=#{newPass},create_time=now(),create_user=#{operator} where user_nm=#{userNm}")
    void updatePass(@Param("userNm")String userNm, @Param("newPass")String newPass,@Param("operator")String operator);

    @Update("update jc.jc_user set role=#{role},passwd=#{newPass},create_time=now(),create_user=#{operator} where user_nm=#{userNm}")
    void updateUser(@Param("userNm")String userNm, @Param("newPass")String newPass,@Param("role")String role,@Param("operator")String operator);

    @Insert("insert into jc.jc_user(user_nm,passwd,role,create_time,create_user) values(#{userNm},#{passwd},#{role},now(),#{operator})")
    void addUser(@Param("userNm")String userNm, @Param("passwd")String passwd,@Param("role")String role,@Param("operator")String operator);

    @Delete("delete from jc.jc_user where user_id=#{userId}")
    void delUser(@Param("userId")String userId);

}
