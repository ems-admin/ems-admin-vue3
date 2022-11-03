package com.ems.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.system.entity.SysLog;
import com.ems.system.entity.dto.QueryDto;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @program: ems-admin-boot
 * @description: this is a interface
 * @author: starao
 * @create: 2021-11-27 14:23
 **/
public interface SysLogService {

    /**
    * @Description: 保存日志
    * @Param: [username, ip, joinPoint, sysLog]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog);

    /**
    * @Description: 查询日志列表
    * @Param: [queryDto, logType]
    * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ems.system.entity.SysLog>
    * @Author: starao
    * @Date: 2021/11/27
    */
    IPage<SysLog> getLogList(QueryDto queryDto, String logType);
}
