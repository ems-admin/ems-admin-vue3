package com.ems.system.service.impl;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.constant.CommonConstants;
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
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(blurry)){
            wrapper.like(SysRole::getRoleName, blurry);
            wrapper.or();
            wrapper.like(SysRole::getRoleCode, blurry);
            wrapper.or();
            wrapper.like(SysRole::getDescription, blurry);
        }
        wrapper.ne(SysRole::getRoleCode, CommonConstants.ROLE_ADMIN);
        return roleMapper.selectList(wrapper);
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
        //  校验角色代码及名称
        checkRole(role);
        if (role.getId() != null){
            roleMapper.updateById(role);
        } else {
            roleMapper.insert(role);
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
        //  校验角色是否已绑定用户
        checkRoleUser(id);
        //  先把与角色绑定的菜单删除
        roleMenuService.deleteByRoleId(id);
        //  再删除角色
        roleMapper.deleteById(id);
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
        return roleMapper.getRoleByUserId(userId);
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
