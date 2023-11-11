package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.dto.RoleMenuDto;
import com.ems.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:05
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("sys")
public class SysRoleMenuController extends ResultUtil {

    private final SysRoleMenuService roleMenuService;

    /**
    * @Description: 通过角色ID获取对应的菜单
    * @Param: [roleId]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("获取角色菜单")
    @GetMapping("/role/menu/list")
    public ResponseEntity<Object> getMenuByRoleId(Long roleId){
        try {
            return success(true, roleMenuService.getMenuByRoleId(roleId));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 授权角色菜单
    * @Param: [roleMenuDto]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("授权角色菜单")
    @PostMapping("/role/menu/edit")
    public ResponseEntity<Object> editMenuRoleByRoleId(@RequestBody RoleMenuDto roleMenuDto){
        try {
            roleMenuService.editMenuRoleByRoleId(roleMenuDto);
            return success(true, "授权成功");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
}
