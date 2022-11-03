package com.ems.config.security;

import com.ems.config.filter.JwtAuthorizationFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:21
 **/
public class JwtConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public JwtConfigurerAdapter(JwtAuthorizationFilter jwtAuthorizationFilter){
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    /**
    * @Description: JWT接管HttpSecurity
    * @Param: [httpSecurity]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    public void configure(HttpSecurity httpSecurity){
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
