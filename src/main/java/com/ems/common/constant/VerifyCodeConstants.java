package com.ems.common.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: ems-vue3
 * @description: this is a class
 * @author: starao
 * @create: 2023-07-11 22:03
 **/
@Getter
@Setter
public class VerifyCodeConstants {

    /**
     * 验证码配置(算数)
     */
    public static String codeType = "arithmetic";
    /**
     * 验证码有效期 分钟
     */
    public static Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    public static int length = 2;
    /**
     * 验证码宽度
     */
    public static int width = 111;
    /**
     * 验证码高度
     */
    public static int height = 28;
    /**
     * 验证码字体
     */
    public static String fontName;
    /**
     * 字体大小
     */
    public static int fontSize = 25;
}
