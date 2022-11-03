package com.ems.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ems.common.constant.CommonConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.SecurityUtil;
import com.ems.common.utils.StringUtil;
import com.ems.system.entity.SysMenu;
import com.ems.system.entity.SysRoleMenu;
import com.ems.system.mapper.SysMenuMapper;
import com.ems.system.mapper.SysRoleMenuMapper;
import com.ems.system.service.SysMenuService;
import com.ems.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:33
 **/
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper menuMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    /**
     * @param roles
     * @Description: 获取菜单树
     * @Param: [roles]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public JSONArray getMenuTree(List<String> roles) {
        try {
            List<SysMenu> menuListAll;
            //  如果角色中包含admin,则直接查询所有非按钮菜单
            if (roles.contains(CommonConstants.ROLE_ADMIN)){
                LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
                wrapper.ne(SysMenu::getType, "3");
                wrapper.orderByAsc(SysMenu::getSort);
                menuListAll = menuMapper.selectList(wrapper);
            //  否则
            } else {
                //  获取当前用户授权的菜单
                menuListAll = menuMapper.getMenuTree(roles);
                if (!CollectionUtils.isEmpty(menuListAll)){
                    Set<SysMenu> menuSet = new HashSet<>();
                    List<SysMenu> list = new ArrayList<>();
                    //  遍历所有菜单
                    for (SysMenu sysMenu : menuListAll) {
                        list.add(sysMenu);
                        //  获取当前菜单的所有上级菜单
                        getAllMenusByChildId(sysMenu.getParentId(), list);
                        for (SysMenu menu : list) {
                            if (menuSet.stream().noneMatch(item -> item.getId().equals(menu.getId()))){
                                menuSet.add(menu);
                            }
                        }
                    }
                    menuListAll = menuSet.stream().sorted(Comparator.comparing(SysMenu::getId)).collect(Collectors.toList());
                }
            }
            JSONArray jsonArray = new JSONArray();
            //  获取最上级菜单
            List<SysMenu> topList = menuListAll.stream().filter(item -> item.getParentId() == 0L).collect(Collectors.toList());
            //  如果最上级菜单不为空
            return getObjects(menuListAll, jsonArray, topList);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 组装树的公共方法
    * @Param: [menuListAll, jsonArray, topList]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2022/10/2
    */
    private JSONArray getObjects(List<SysMenu> menuListAll, JSONArray jsonArray, List<SysMenu> topList) {
        if (!CollectionUtils.isEmpty(topList)){
            //  组装菜单树
            for (SysMenu sysMenu : topList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", sysMenu.getId());
                jsonObject.put("parentId", sysMenu.getParentId());
                jsonObject.put("name", sysMenu.getName());
                jsonObject.put("path", sysMenu.getPath());
                jsonObject.put("icon", sysMenu.getIcon());
                jsonObject.put("sort", sysMenu.getSort());
                jsonObject.put("component", sysMenu.getComponent());
                jsonObject.put("permission", sysMenu.getPermission());
                jsonObject.put("type", sysMenu.getType());
                if (!CollectionUtils.isEmpty(getChildById(menuListAll, sysMenu.getId()))){
                    jsonObject.put("children", getChildById(menuListAll, sysMenu.getId()));
                }

                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
    * @Description: 通过ID获取子菜单
    * @Param: [menuList, parentId]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: starao
    * @Date: 2022/10/1
    */
    private JSONArray getChildById(List<SysMenu> menuList, long parentId){
        JSONArray jsonArray = new JSONArray();
        List<SysMenu> children = menuList.stream().filter(item -> item.getParentId().equals(parentId)).collect(Collectors.toList());
        return getObjects(menuList, jsonArray, children);
    }

    /**
     * @param sysMenu
     * @Description: 编辑菜单
     * @Param: [sysMenu]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void editMenu(SysMenu sysMenu) {
        try {
            if (sysMenu.getId() != null){
                menuMapper.updateById(sysMenu);
            } else {
                menuMapper.insert(sysMenu);
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param id
     * @Description: 删除菜单
     * @Param: [id]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void delMenu(Long id) {
        try {
            //  校验菜单是否已绑定角色
            checkMenuRole(id);
            menuMapper.deleteById(id);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roles
     * @Description: 查询当前用户所有权限菜单
     * @Param: [roles]
     * @return: java.util.List<com.ems.system.entity.SysMenu>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public List<SysMenu> queryAllMenus(List<String> roles) {
        try {
            if (roles.contains(CommonConstants.ROLE_ADMIN)){
                return menuMapper.selectList(null);
            }
            return menuMapper.getMenuTree(roles);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param blurry
     * @Description: 获取菜单列表
     * @Param: [blurry]
     * @return: com.alibaba.fastjson.JSONArray
     * @Author: starao
     * @Date: 2021/12/11
     */
    @Override
    public JSONArray getMenuTable(String blurry) {
        try {
            JSONArray jsonArray = new JSONArray();
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            if (StringUtil.isNotBlank(blurry)){
                wrapper.like(SysMenu::getName, blurry);
                wrapper.or();
                wrapper.like(SysMenu::getPath, blurry);
            }
            wrapper.orderByAsc(SysMenu::getSort);
            List<SysMenu> list = menuMapper.selectList(wrapper);
            List<SysMenu> topList = list.stream().filter(item -> item.getParentId() == 0L).collect(Collectors.toList());
            return getObjects(list, jsonArray, topList);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @param roles
     * @Description: 通过角色获取所有授权菜单
     * @Param: [currentRoles]
     * @return: java.util.List<java.lang.String>
     * @Author: starao
     * @Date: 2022/1/19
     */
    @Override
    public List<String> getUrlsByRoles(List<String> roles) {
        try {
            return menuMapper.getMenuUrlByRole(roles);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
     * @Description: 获取权限列表
     * @Param: []
     * @return: java.util.List<java.lang.String>
     * @Author: starao
     * @Date: 2022/10/6
     */
    @Override
    public List<String> getPermission() {
        try {
            List<String> roles = SecurityUtil.getCurrentRoles();
            List<String> permissions;
            if (roles.contains(CommonConstants.ROLE_ADMIN)){
                LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
                wrapper.select(SysMenu::getPermission);
                wrapper.eq(SysMenu::getType, "3");
                permissions = menuMapper.selectObjs(wrapper).stream().map(o -> (String)o).collect(Collectors.toList());
            } else {
                permissions = menuMapper.getPermission(roles);
            }
            return permissions;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 校验菜单是否已绑定角色
    * @Param: [menuId]
    * @return: void
    * @Author: starao
    * @Date: 2021/12/4
    */
    private void checkMenuRole(Long menuId){
        try {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getMenuId, menuId);
            long count = roleMenuMapper.selectCount(wrapper);
            if (count > 0){
                throw new BadRequestException("该菜单已绑定角色，无法删除");
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }

    /**
    * @Description: 通过菜单Id获取所有上级菜单
    * @Param: [menuId]
    * @Author: starao
    * @Date: 2021/12/17
    */
    private void getAllMenusByChildId(Long menuId, List<SysMenu> list){
        try {
            SysMenu sysMenu = menuMapper.selectById(menuId);
            //  如果当前菜单不是最顶级的菜单
            if (sysMenu != null){
                list.add(sysMenu);
                //  如果当前菜单的上级菜单也不是最顶级的菜单
                if (!sysMenu.getParentId().equals(0L)){
                    getAllMenusByChildId(sysMenu.getParentId(), list);
                }
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMsg());
        }
    }
}
