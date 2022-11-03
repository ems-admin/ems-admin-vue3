package com.ems.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:01
 **/
public class StringUtil extends StringUtils {

    private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

    private static final String UNKNOWN = "unknown";

    /**
    * @Description: 获取请求ip地址
    * @Param: [request]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String localhost = "127.0.0.1";
        //  取第一个ip
        ip = ip.split(",")[0];
        if (localhost.equals(ip)) {
            // 获取本机真正的ip
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }
}
