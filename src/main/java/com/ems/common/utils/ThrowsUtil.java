package com.ems.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:09
 **/
public class ThrowsUtil {

    /**
    * @Description: 获取错误的堆栈信息
    * @Param: [throwable]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
