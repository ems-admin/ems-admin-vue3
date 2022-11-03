package com.ems.system.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:39
 **/
@Setter
@Getter
public class MenuDto {

    private Long id;

    private String title;

    private String url;

    private String parentId;

    private int sort;

    private String type;

    private String permission;
}
