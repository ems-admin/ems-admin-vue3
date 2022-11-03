package com.ems.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.exception.BadRequestException;
import com.ems.system.entity.SysRoleUser;
import com.ems.system.mapper.SysRoleUserMapper;
import com.ems.system.service.SysRoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:43
 **/
@Service
@RequiredArgsConstructor
public class SysRoleUserServiceImpl implements SysRoleUserService {

    private final SysRoleUserMapper roleUserMapper;

    /**
     * @param roleId
     * @Description: 通过角色ID查询绑定的用户
     * @Param: [roleId]
     * @return: java.util.List<com.ems.system.entity.SysRoleUser>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysRoleUser> getRoleUserByRoleId(Long roleId) {
        try {
            QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", roleId);
            return roleUserMapper.selectList(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @param roleIds
     * @Description: 修改用户角色
     * @Param: [userId, roles]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    @Transactional
    public void edit(Long userId, List<String> roleIds) {
        try {
            //  首先清空该用户所有角色
            QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            roleUserMapper.delete(wrapper);
            //  然后将用户与角色绑定
            roleIds.forEach(role -> {
                SysRoleUser roleUser = new SysRoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(Long.parseLong(role));
                roleUserMapper.insert(roleUser);
            });
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @Description: 查询当前用户角色
     * @Param: [userId]
     * @return: java.util.List<com.ems.system.entity.SysRoleUser>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysRoleUser> getRoleUserByUserId(Long userId) {
        try {
            QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            return roleUserMapper.selectList(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @Description: 删除用户与角色的绑定
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void deleteByUserId(String userId) {
        try {
            LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleUser::getUserId, userId);
            roleUserMapper.delete(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }
}
