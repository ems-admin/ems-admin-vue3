package com.ems.system.controller;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.ResultUtil;
import com.ems.system.entity.dto.QueryDto;
import com.ems.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 17:00
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysLogController extends ResultUtil {

    private final SysLogService logService;

    /**
    * @Description: 查询日志列表
    * @Param: [queryDto, logType]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @GetMapping("/log/list")
    public ResponseEntity<Object> getLogList(QueryDto queryDto, String logType){
        try {
            return success(true, logService.getLogList(queryDto, logType));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
}
