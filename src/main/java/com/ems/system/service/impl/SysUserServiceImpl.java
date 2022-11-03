package com.ems.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.constant.CommonConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.SecurityUtil;
import com.ems.common.utils.StringUtil;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.QueryDto;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.mapper.SysUserMapper;
import com.ems.system.service.SysRoleUserService;
import com.ems.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:44
 **/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final SysRoleUserService roleUserService;

    /**
     * @param userName
     * @Description: 根据用户名查询用户
     * @Param: [userName]
     * @return: com.ems.system.entity.SysUser
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public SysUser findByName(String userName) {
        try {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("username", userName);
            return sysUserMapper.selectOne(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userDto
     * @Description: 编辑用户
     * @Param: [userDto]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void editUser(UserDto userDto) {
        try {
            checkUser(userDto);
            SysUser user = new SysUser();
            user.setPassword(userDto.getPassword());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setId(userDto.getId());
            user.setNickName(userDto.getNickName());
            if (userDto.getEnabled() != null){
                user.setEnabled(userDto.getEnabled());
            }
            if (user.getId() != null){
                sysUserMapper.updateById(user);
            } else {
                //  初始化用户密码.默认111111
                user.setPassword(passwordEncoder.encode(CommonConstants.DEFAULT_PASSWORD));
                sysUserMapper.insert(user);
            }

            //  如果带有角色,就修改角色
            if (!CollectionUtils.isEmpty(userDto.getRoleIds()) && user.getId() != null){
                roleUserService.edit(user.getId(), userDto.getRoleIds());
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param queryDto
     * @Description: 查询用户列表
     * @Param: [blurry]
     * @return: java.util.List<com.ems.system.entity.SysUser>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public IPage<UserDto> queryUserTable(QueryDto queryDto) {
        try {
            Page<UserDto> page = new Page<>();
            page.setCurrent(queryDto.getCurrentPage());
            page.setSize(queryDto.getSize());
            return sysUserMapper.queryUserTable(page, queryDto.getBlurry());
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param username
     * @Description: 根据用户名查询用户
     * @Param: [username]
     * @return: com.ems.system.entity.dto.UserDto
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public UserDto loadByName(String username) {
        try {
            return sysUserMapper.loadByName(username);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param id
     * @Description: 删除用户
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    @Transactional
    public void delUser(String id) {
        try {
            //  先解除用户与角色的绑定
            roleUserService.deleteByUserId(id);
            //  再删除用户
            sysUserMapper.deleteById(id);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param sysUser
     * @Description: 修改用户状态
     * @Param: [sysUser]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void enabledUser(SysUser sysUser) {
        try {
            sysUserMapper.updateById(sysUser);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param jsonObject
     * @Description: 修改用户密码
     * @Param: [jsonObject]
     * @return: void
     * @Author: starao
     * @Date: 2022/10/6
     */
    @Override
    public void updatePassword(JSONObject jsonObject) {
        try {
            String password = jsonObject.getString("password");
            String newPassword = jsonObject.getString("newPassword");
            String confirmPassword = jsonObject.getString("confirmPassword");

            //  获取当前登录用户
            SysUser user = sysUserMapper.selectById(SecurityUtil.getCurrentUserId());
            String pwd = user.getPassword();
            //  校验原密码
            if (!passwordEncoder.matches(password, pwd)){
                throw new BadRequestException("原密码错误，请重新输入");
            }
            //  校验新密码与确认密码
            if (!newPassword.equals(confirmPassword)){
                throw new BadRequestException("新密码与确认密码不相同，请重新输入");
            }
            //  修改密码为新密码
            user.setPassword(passwordEncoder.encode(newPassword));
            sysUserMapper.updateById(user);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 校验用户名和昵称
    * @Param: [userDto]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    private void checkUser(UserDto userDto){
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if (userDto.getId() != null){
            wrapper.ne(SysUser::getId, userDto.getId());
        }
        if (StringUtil.isNotBlank(userDto.getUsername()) && StringUtil.isNotBlank(userDto.getNickName())){
            wrapper.and(w -> w.eq(SysUser::getUsername, userDto.getUsername()).or().eq(SysUser::getNickName, userDto.getNickName()));
        }
        long count = sysUserMapper.selectCount(wrapper);
        if (count > 0){
            throw new BadRequestException("用户名或昵称已存在，请重新输入");
        }
    }
}
