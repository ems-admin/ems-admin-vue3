package com.ems.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ems.system.entity.SysMenu;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:24
 **/
public interface SysMenuService {

    /**
    * @Description: 获取菜单树
    * @Param: [roles]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/11/27
    */
    JSONArray getMenuTree(List<String> roles);

    /**
    * @Description: 编辑菜单
    * @Param: [sysMenu]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void editMenu(SysMenu sysMenu);

    /**
    * @Description: 删除菜单
    * @Param: [id]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void delMenu(Long id);

    /**
    * @Description: 查询当前用户所有权限菜单
    * @Param: [roles]
    * @return: java.util.List<com.ems.system.entity.SysMenu>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysMenu> queryAllMenus(List<String> roles);

    /**
    * @Description: 获取菜单列表
    * @Param: [blurry]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2021/12/11
    */
    JSONArray getMenuTable(String blurry);

    /**
    * @Description: 通过角色获取所有授权菜单
    * @Param: [currentRoles]
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2022/1/19
    */
    List<String> getUrlsByRoles(List<String> currentRoles);

    /**
    * @Description: 获取权限列表
    * @Param: []
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2022/10/6
    */
    List<String> getPermission();

    /**
    * @Description: 获取菜单下拉树
    * @Param: []
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2022/11/9
    */
    JSONArray getMenuTreeSelect();
}
