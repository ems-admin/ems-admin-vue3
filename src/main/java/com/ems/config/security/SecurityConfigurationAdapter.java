package com.ems.config.security;

import com.ems.config.filter.JwtAuthorizationFilter;
import com.ems.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:40
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    
    private final SecurityProblemSupport securityProblemSupport;

    private final SysMenuService menuService;

    /**
    * @Description: 配置SpringSecurity推荐的加密方式
    * @Param: []
    * @return: org.springframework.security.crypto.password.PasswordEncoder
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
    * @Description: 设置HttpSecurity的安全策略
    * @Param: [httpSecurity]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */

    /**
    * @Description: 配置不进入SpringSecurity的名单,如果要在SpringSecurity中配置，需要在JwtAuthorizationFilter单独处理
    * @Param: [webSecurity]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring()
                .antMatchers(HttpMethod.GET, "/")
                .antMatchers(HttpMethod.GET, "index.html")
                .antMatchers(HttpMethod.GET, "/favicon.ico")
                .antMatchers(HttpMethod.POST,"/auth/login")
                .antMatchers(HttpMethod.PUT, "/auth/refresh")
                .antMatchers("/js/**")
                .antMatchers("/fonts/**")
                .antMatchers("/img/**")
                .antMatchers("/css/**");
    }
    
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // 当用户无权访问资源时发送 401 响应
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // 当用户访问资源因权限不足时发送 403 响应
                .accessDeniedHandler(securityProblemSupport)
                .and()
                // 禁用 CSRF
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // 配置不需要验证的请求地址
                // 其他请求需验证
                .anyRequest().authenticated()
                .and()
                // 不需要 session（不创建会话）
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(securityConfigurationAdapter());
    }

    /**
    * @Description: 将HttpSecurity的安全策略移交给JWT
    * @Param: []
    * @return: com.ems.config.security.JwtConfigurerAdapter
    * @Author: starao
    * @Date: 2021/11/27
    */
    private JwtConfigurerAdapter securityConfigurationAdapter() throws Exception {
        return new JwtConfigurerAdapter(new JwtAuthorizationFilter(authenticationManager(), menuService));
    }

}
