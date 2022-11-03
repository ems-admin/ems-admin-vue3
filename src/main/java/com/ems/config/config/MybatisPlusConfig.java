package com.ems.config.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:10
 **/
@Configuration
@MapperScan("com.ems.system.mapper")
public class MybatisPlusConfig {

    /**
    * @Description: 配置mybatis-plus分页工具
    * @Param: []
    * @return: com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
