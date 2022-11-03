package com.ems.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:05
 **/
@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static final List<CallBack> CALL_BACKS = new ArrayList<>();

    private static boolean addCallback = true;

    /**
    * @Description: 从applicationContext中获取Bean并自动转化为所赋值对象的类型
    * @Param: [requiredType]
    * @return: T
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static <T> T getBean(Class<T> requiredType){
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
    * @Description: 校验ApplicationContext
    * @Param: []
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    private static void assertContextInjected(){
        if (applicationContext == null){
            throw new IllegalStateException("applicationContext属性未注入，" +
                    "请在SpringBoot启动类中注册SpringContextHolder.");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null){
            log.info("SpringContextHolder中的ApplicationContext被覆盖，" +
                    "原ApplicationContext为：" + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
        if (addCallback){
            for (CallBack callBack : SpringContextHolder.CALL_BACKS) {
                callBack.executor();
            }
        }
        SpringContextHolder.addCallback = false;
    }

    @Override
    public void destroy() {
        log.info("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }
}
