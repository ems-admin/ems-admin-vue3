package com.ems.config.filter;


import com.ems.common.constant.SecurityConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.JwtUtil;
import com.ems.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 13:15
 **/
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final SysMenuService menuService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SysMenuService menuService) {
        super(authenticationManager);
        this.menuService = menuService;
    }

    /**
    * @Description: 过滤用户请求
    * @Param: [request, response, filterChain]
    * @return: void
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            //  从request中获取token
            String token = this.getTokenFromHttpServletRequest(request);
            //  校验token是否有效
            if (JwtUtil.verifyToken(token)){
                //  获取认证信息
                Authentication authentication = JwtUtil.getAuthentication(token);
                //  将认证信息保存在spring安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //  放行请求
                filterChain.doFilter(request, response);
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
