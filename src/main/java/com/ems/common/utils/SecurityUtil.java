package com.ems.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ems.common.exception.BadRequestException;
import com.ems.system.entity.SysUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:00
 **/
public class SecurityUtil {

    /**
    * @Description: 获取当前登录的用户
    * @Param: []
    * @return: org.springframework.security.core.userdetails.UserDetails
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
    * @Description: 获取系统用户名称
    * @Param: []
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
    * @Description: 获取系统用户ID
    * @Param: []
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        if (userDetails != null){
            return JSON.parseObject(JSONObject.toJSONString(userDetails)).getJSONObject("user").getString("id");
        } else {
            return null;
        }
    }

    /**
    * @Description: 查询系统用户角色列表
    * @Param: []
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static List<String> getCurrentRoles(){
        UserDetails userDetails = getCurrentUser();
        List<String> list = new ArrayList<>();
        if (userDetails != null){
            userDetails.getAuthorities().forEach((item) -> {
                list.add(item.toString());
            });
        }
        return list;
    }
}
