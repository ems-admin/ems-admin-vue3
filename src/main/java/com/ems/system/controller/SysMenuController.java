package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.common.utils.SecurityUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysMenu;
import com.ems.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:01
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysMenuController extends ResultUtil {

    private final SysMenuService menuService;

    /**
    * @Description: 获取菜单树
    * @Param: []
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("获取菜单树")
    @GetMapping("/menu/tree")
    public ResponseEntity<Object> getMenuTree(){
        try {
            List<String> roles = SecurityUtil.getCurrentRoles();
            return success(true, menuService.getMenuTree(roles));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 获取权限列表
    * @Param: []
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2022/10/6
    */
    @Log(value = "获取权限列表")
    @GetMapping("/menu/permission")
    public ResponseEntity<Object> getPermission(){
        try {
            return success(true, menuService.getPermission());
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 查询当前用户所有权限菜单
    * @Param: []
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @GetMapping("/menu/all")
    public ResponseEntity<Object> queryAllMenus(){
        try {
            List<String> roles = SecurityUtil.getCurrentRoles();
            return success(true, menuService.queryAllMenus(roles));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 获取菜单列表
    * @Param: [blurry]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/12/11
    */
    @Log("获取菜单列表")
    @GetMapping("/menu/table")
    public ResponseEntity<Object> getMenuTable(String blurry){
        try {
            return success(true, menuService.getMenuTable(blurry));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 编辑菜单
    * @Param: [sysMenu]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("编辑菜单")
    @PostMapping("/menu/edit")
    public ResponseEntity<Object> editMenu(@RequestBody SysMenu sysMenu){
        try {
            menuService.editMenu(sysMenu);
            return success(true, sysMenu.getId() == null ? "添加成功" : "修改成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 删除菜单
    * @Param: [id]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("删除菜单")
    @DeleteMapping("/menu/del")
    public ResponseEntity<Object> delMenu(Long id){
        try {
            menuService.delMenu(id);
            return success(true, "删除成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
}
