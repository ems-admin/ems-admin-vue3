package com.ems.common.constant;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:48
 **/
public class CommonConstants {

    private CommonConstants(){
        throw new IllegalStateException("无法创建静态常量类");
    }

    /**
     * 超级管理员角色
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "111111";
}
