package com.ems.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.exception.BadRequestException;
import com.ems.system.entity.SysRole;
import com.ems.system.entity.SysRoleUser;
import com.ems.system.mapper.SysRoleMapper;
import com.ems.system.service.SysRoleMenuService;
import com.ems.system.service.SysRoleService;
import com.ems.system.service.SysRoleUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:39
 **/
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysRoleUserService roleUserService;

    private final SysRoleMenuService roleMenuService;

    /**
     * @param blurry
     * @Description: 获取角色列表
     * @Param: [blurry]
     * @return: java.util.List<com.ems.system.entity.SysRole>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysRole> getRoleList(String blurry) {
        try {
            LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(blurry)){
                wrapper.like(SysRole::getRoleName, blurry);
                wrapper.or();
                wrapper.like(SysRole::getRoleCode, blurry);
                wrapper.or();
                wrapper.like(SysRole::getDescription, blurry);
            }
            return roleMapper.selectList(wrapper);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param role
     * @Description: 编辑角色
     * @Param: [role]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void editRole(SysRole role) {
        try {
            //  校验角色代码及名称
            checkRole(role);
            if (role.getId() != null){
                roleMapper.updateById(role);
            } else {
                roleMapper.insert(role);
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param id
     * @Description: 删除角色
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    @Transactional
    public void delRole(Long id) {
        try {
            //  校验角色是否已绑定用户
            checkRoleUser(id);
            //  先把与角色绑定的菜单删除
            roleMenuService.deleteByRoleId(id);
            //  再删除角色
            roleMapper.deleteById(id);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @Description: 角色选择框列表
     * @Param: [userId]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public JSONArray getAllRoleForXm(Long userId) {
        try {
            //  查询所有角色
            List<SysRole> list = roleMapper.selectList(null);
            //  查询当前用户角色
            List<SysRoleUser> roleUserList = roleUserService.getRoleUserByUserId(userId);
            JSONArray jsonArray = new JSONArray();
            if (!CollectionUtils.isEmpty(list)){
                for (SysRole role : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", role.getRoleName());
                    jsonObject.put("id", role.getId());

                    if (!CollectionUtils.isEmpty(roleUserList) && roleUserList.get(0).getRoleId().equals(role.getId())){
                        jsonObject.put("selected", true);
                    }
                    jsonArray.add(jsonObject);
                }
            }
            return jsonArray;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @Description: 通过用户ID获取当前用户角色
     * @Param: [userId]
     * @return: java.util.List<com.ems.system.entity.SysRole>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        try {
            return roleMapper.getRoleByUserId(userId);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param userId
     * @Description: 获取用户当前角色列表
     * @Param: [id]
     * @return: java.util.List<org.springframework.security.core.GrantedAuthority>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<GrantedAuthority> getRolesByUser(Long userId) {
        Set<String> permissions = new HashSet<>();
        List<SysRole> roleList = roleMapper.getRoleByUserId(userId);
        if (!CollectionUtils.isEmpty(roleList)){
            for (SysRole sysRole : roleList) {
                permissions.add(sysRole.getRoleCode());
            }
        }
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
    * @Description: 校验角色名称和代码
    * @Param: [role]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    private void checkRole(SysRole role){
        try {
            List<SysRole> list;
            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
            if (role.getId() != null){
                wrapper.ne("id", role.getId());
            }
            wrapper.and(w -> w.eq("role_name", role.getRoleName()).or().eq("role_code", role.getRoleCode()));
            list = roleMapper.selectList(wrapper);
            if (list != null && !list.isEmpty()){
                throw new BadRequestException("角色代码或名称已存在，请重新输入");
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 校验角色是否已绑定用户
    * @Param: [roleId]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    private void checkRoleUser(Long roleId){
        List<SysRoleUser> list = roleUserService.getRoleUserByRoleId(roleId);
        if (!CollectionUtils.isEmpty(list)){
            throw new BadRequestException("该角色已绑定用户，请解绑后再删除");
        }
    }
}
