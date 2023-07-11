package com.ems.logs.ascept;

import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.RequestHolder;
import com.ems.common.utils.SecurityUtil;
import com.ems.common.utils.StringUtil;
import com.ems.common.utils.ThrowsUtil;
import com.ems.system.entity.SysLog;
import com.ems.system.service.SysLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:11
 **/
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final SysLogService sysLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(SysLogService sysLogService){
        this.sysLogService = sysLogService;
    }

    /**
    * @Description: 配置切入点
    * @Param: []
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Pointcut("@annotation(com.ems.logs.annotation.Log)")
    public void logPointcut(){

    }

    /**
    * @Description: 配置环绕通知,使用在方法logPointcut()上注册的切入点
    * @Param: [joinPoint]
    * @return: java.lang.Object
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        SysLog sysLog = new SysLog("1", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        sysLogService.save(getUsername(), StringUtil.getIp(request),joinPoint, sysLog);
        return result;
    }

    /**
    * @Description: 配置异常通知
    * @Param: [joinPoint, e]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    @Transactional(rollbackFor = Exception.class)
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
        SysLog sysLog = new SysLog("2",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        sysLog.setExceptionDetail(ThrowsUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        sysLogService.save(getUsername(), StringUtil.getIp(request), (ProceedingJoinPoint)joinPoint, sysLog);
    }

    /**
    * @Description: 获取当前登录用户名
    * @Param: []
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public String getUsername() {
        try {
            return SecurityUtil.getCurrentUsername();
        }catch (Exception e){
            return "";
        }
    }
}
