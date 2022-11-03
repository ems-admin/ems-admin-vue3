package com.ems.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:59
 **/
public class ResultUtil {

    /**
    * @Description: 成功结果
    * @Param: [code, data]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static ResponseEntity<Object> success(boolean code, Object data){
        Map<String, Object> map = new HashMap<>(4);
        map.put("success", code);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
    * @Description: 失败结果
    * @Param: [code, msg]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static ResponseEntity<Object> fail(boolean code, String msg){
        Map<String, Object> map = new HashMap<>(4);
        map.put("success", code);
        map.put("msg", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
