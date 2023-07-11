package com.ems.system.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:30
 **/
@Setter
@Getter
public class UserDto {

    private Long id;

    private String username;

    private String nickName;

    private String email;

    private String password;

    private List<String> roles;

    private Boolean enabled;

    private List<String> roleIds;

    private String uuid;

    private String code;
}
