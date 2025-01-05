package com.ems.system.service;


import com.alibaba.fastjson2.JSONArray;
import com.ems.system.entity.SysRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:26
 **/
public interface SysRoleService {

    /**
    * @Description: 获取角色列表
    * @Param: [blurry]
    * @return: java.util.List<com.ems.system.entity.SysRole>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysRole> getRoleList(String blurry);

    /**
    * @Description: 编辑角色
    * @Param: [role]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void editRole(SysRole role);

    /**
    * @Description: 删除角色
    * @Param: [id]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void delRole(Long id);

    /**
    * @Description: 角色选择框列表
    * @Param: [userId]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    JSONArray getAllRoleForXm(Long userId);

    /**
    * @Description: 通过用户ID获取当前用户角色
    * @Param: [userId]
    * @return: java.util.List<com.ems.system.entity.SysRole>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysRole> getRoleByUserId(Long userId);

    /**
    * @Description: 获取用户当前角色列表
    * @Param: [id]
    * @return: java.util.List<org.springframework.security.core.GrantedAuthority>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<GrantedAuthority> getRolesByUser(Long id);
}
