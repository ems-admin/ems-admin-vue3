package com.ems.system.service;

import com.ems.system.entity.SysRoleMenu;
import com.ems.system.entity.dto.RoleMenuDto;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:26
 **/
public interface SysRoleMenuService {

    /**
    * @Description: 通过角色ID获取对应的菜单
    * @Param: [roleId]
    * @return: java.util.List<com.ems.system.entity.SysRoleMenu>
    * @Author: starao
    * @Date: 2021/11/27
    */
    List<SysRoleMenu> getMenuByRoleId(Long roleId);

    /**
    * @Description: 授权角色菜单
    * @Param: [roleMenuDto]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void editMenuRoleByRoleId(RoleMenuDto roleMenuDto);

    /**
    * @Description: 删除与角色绑定的菜单
    * @Param: [roleId]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void deleteByRoleId(Long roleId);
}
