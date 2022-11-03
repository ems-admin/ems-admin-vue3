package com.ems.system.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: ems-vue2
 * @description: this is a class
 * @author: starao
 * @create: 2022-10-03 16:43
 **/
@Setter
@Getter
public class QueryDto {

    private String blurry;

    private long currentPage = 1;

    private long size = 10;
}
