package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.common.utils.StringUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysRole;
import com.ems.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:03
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysRoleController extends ResultUtil {

    private final SysRoleService roleService;

    /**
    * @Description: 获取角色列表
    * @Param: [blurry]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("获取角色列表")
    @GetMapping("/role/table")
    public ResponseEntity<Object> getRoleList(String blurry){
        try {
            return success(true, roleService.getRoleList(blurry));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 编辑角色
    * @Param: [role]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("编辑角色")
    @PostMapping("/role/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> editRole(@RequestBody SysRole role){
        try {
            String tag = StringUtil.getEditType(role.getId());
            roleService.editRole(role);
            return success(true, tag);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 删除角色
    * @Param: [id]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("删除角色")
    @DeleteMapping("/role/del")
    public ResponseEntity<Object> delRole(Long id){
        try {
            roleService.delRole(id);
            return success(true, "删除成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 角色选择框列表
    * @Param: [userId]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("获取角色选择框列表")
    @GetMapping("/role/select")
    public ResponseEntity<Object> getAllRoleForXm(Long userId){
        try {
            return success(true, roleService.getAllRoleForXm(userId));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
}
