package com.ems.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.common.utils.StringUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.QueryDto;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:06
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysUserController extends ResultUtil {

    private final SysUserService userService;

    /**
    * @Description: 查询用户列表
    * @Param: [blurry]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("查询用户列表")
    @GetMapping("/user/table")
    public ResponseEntity<Object> queryUserTable(QueryDto queryDto){
        try {
            return success(true, userService.queryUserTable(queryDto));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 编辑用户
    * @Param: [userDto]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("编辑用户")
    @PostMapping("/user/edit")
    public ResponseEntity<Object> editUser(@RequestBody UserDto userDto){
        try {
            String str = StringUtil.getEditType(userDto.getId());
            userService.editUser(userDto);
            return success(true, str);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 删除用户
    * @Param: [id]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("删除用户")
    @DeleteMapping("/user/del")
    public ResponseEntity<Object> delUser(String id){
        try {
            userService.delUser(id);
            return success(true, "删除成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, "删除失败");
        }
    }

    /**
    * @Description: 修改用户状态
    * @Param: [sysUser]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("改变用户状态")
    @PutMapping("/user/enabled")
    public ResponseEntity<Object> enabledUser(@RequestBody SysUser sysUser){
        String str = sysUser.isEnabled() ? "启用" : "停用";
        try {
            userService.enabledUser(sysUser);
            return success(true, str + "成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 修改用户密码
    * @Param: [jsonObject]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2022/10/6
    */
    @Log(value = "修改用户密码")
    @PutMapping("/user/password")
    public ResponseEntity<Object> updatePassword(@RequestBody JSONObject jsonObject){
        try {
            userService.updatePassword(jsonObject);
            return success(true, "修改成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, "修改失败");
        }
    }
}
