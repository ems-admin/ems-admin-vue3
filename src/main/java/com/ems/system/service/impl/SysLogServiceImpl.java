package com.ems.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.StringUtil;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysLog;
import com.ems.system.entity.dto.QueryDto;
import com.ems.system.mapper.SysLogMapper;
import com.ems.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:30
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {

    private final SysLogMapper logMapper;

    /**
     * @param username
     * @param ip
     * @param joinPoint
     * @param sysLog
     * @Description: 保存日志
     * @Param: [username, ip, joinPoint, sysLog]
     * @return: void
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        // 描述
        if (sysLog != null) {
            sysLog.setDescription(aopLog.value());
        }
        assert sysLog != null;
        sysLog.setIp(ip);

        //  如果是登录
        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                username = JSON.parseObject(JSONObject.toJSONString(argValues.get(0))).getString("username");
            } catch (BadRequestException e) {
                SysLogServiceImpl.log.error(e.getMessage(), e);
            }
        }
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(params + " }");
        if (sysLog.getId() == null) {
            logMapper.insert(sysLog);
        } else {
            logMapper.updateById(sysLog);
        }
    }

    /**
     * @param queryDto
     * @param logType
     * @Description: 查询日志列表
     * @Param: [queryDto, logType]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ems.system.entity.SysLog>
     * @Author: starao
     * @Date: 2021/11/27
     */
    @Override
    public IPage<SysLog> getLogList(QueryDto queryDto, String logType) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysLog::getCreateTime);
        if (StringUtil.isNotBlank(logType)){
            wrapper.eq(SysLog::getLogType, logType);
        }
        if (StringUtil.isNotBlank(queryDto.getBlurry())){
            wrapper.and(q -> q.like(SysLog::getUsername, queryDto.getBlurry())
                    .or().like(SysLog::getDescription, queryDto.getBlurry()));
        }
        Page<SysLog> page = new Page<>();
        page.setSize(queryDto.getSize());
        page.setCurrent(queryDto.getCurrentPage());
        return logMapper.selectPage(page, wrapper);
    }
}
