package com.ems.system.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:39
 **/
@Setter
@Getter
public class RoleMenuDto {

    private Long roleId;

    private Set<Long> menuIds;
}
