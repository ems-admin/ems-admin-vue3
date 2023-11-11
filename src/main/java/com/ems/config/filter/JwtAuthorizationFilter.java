package com.ems.config.filter;


import com.ems.common.constant.SecurityConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.JwtUtil;
import com.ems.common.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:15
 **/
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    /**
    * @Description: 过滤用户请求
    * @Param: [request, response, filterChain]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("请求路径:" + request.getRequestURI());
            //  从request中获取token
            String token = this.getTokenFromHttpServletRequest(request);
            //  如果token不存在或者携带了刷新token(长度小于150,可以根据自己生成的refreshToken来判断),
            //  直接放行,由系统Security判断是否具有访问权限
            if (StringUtil.isBlank(token) || token.length() < 150){
                filterChain.doFilter(request, response);
                return;
            }
            //  校验token是否有效
            if (JwtUtil.verifyToken(token)){
                //  获取认证信息
                Authentication authentication = JwtUtil.getAuthentication(token);
                //  将认证信息保存在spring安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //  此处为部署后前后端不分离配置需要,如果是前后端分离的部署(即使用nginx进行部署,可删除此if代码块)
                if (request.getRequestURI().startsWith("/api/")) {
                    // 修改请求路径，去除 "/api/" 前缀
                    String newRequestURI = request.getRequestURI().substring(4);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(newRequestURI);
                    dispatcher.forward(request, response);
                } else {
                    //  放行请求
                    filterChain.doFilter(request, response);
                }
            }
        } catch (BadRequestException e) {
            //  token问题,统一作401处理
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    /**
    * @Description: 从http中获取token
    * @Param: [request]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    private String getTokenFromHttpServletRequest(HttpServletRequest request){
        //  通过Authorization获取token
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        return null;
    }
}
