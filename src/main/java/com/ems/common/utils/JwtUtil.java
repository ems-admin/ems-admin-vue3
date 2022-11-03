package com.ems.common.utils;

import com.ems.common.constant.SecurityConstants;
import com.ems.common.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:47
 **/
@Slf4j
public class JwtUtil {

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private static final byte[] refreshKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_REFRESH_KEY);

    private JwtUtil(){
        throw new IllegalStateException("禁止创建当前对象");
    }

    /**
    * @Description: 根据用户和用户角色生成 token
    * @Param: [userName, roles, isRemember]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String generateToken(String userName, List<String> roles, Boolean isRemember){
        try {
            //  过期时间
            long expirationTime = isRemember ? SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME : SecurityConstants.TOKEN_EXPIRATION_TIME;

            //  生成token
            return Jwts.builder()
                    //  生成签证信息
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                    .signWith(Keys.hmacShaKeyFor(secretKey))
                    //  所有人
                    .setSubject(userName)
                    //  角色
                    .claim(SecurityConstants.TOKEN_ROLE_CLAIM, roles)
                    //  JWT主体
                    .setIssuer(SecurityConstants.TOKEN_ISSUER)
                    //  签发时间
                    .setIssuedAt(new Date())
                    .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                    //  设置有效时间
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                    .compact();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
    * @Description: 生成refresh_token
    * @Param: [userName]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2022/10/5
    */
    public static String getRefreshToken(String userName){
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(refreshKey))
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME * 1000))
                .compact();
    }

    /**
    * @Description: 校验token是否有效
    * @Param: [token]
    * @return: boolean
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static boolean verifyToken(String token){
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("token 已过期 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("token 不支持 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("token 格式不正确 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (SignatureException e) {
            log.warn("token 签名无效 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("token 不能为空 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
    * @Description: 根据token获取用户认证信息
    * @Param: [token]
    * @return: org.springframework.security.core.Authentication
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static Authentication getAuthentication(String token) {
        Claims claims;
        try {
            claims = getTokenBody(token);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            claims = e.getClaims();
        }
        //  获取用户角色
        List<String> roles = (List<String>) claims.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        List<SimpleGrantedAuthority> authorities =
                CollectionUtils.isEmpty(roles) ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) :
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        //  获取用户名
        User principal = new User(claims.getSubject(), "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
    * @Description: 从token中获取用户信息
    * @Param: [token]
    * @return: io.jsonwebtoken.Claims
    * @Author: starao
    * @Date: 2021/11/27
    */
    private static Claims getTokenBody(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
    * @Description: 从refreshToken中获取用户信息
    * @Param: [refreshToken]
    * @return: io.jsonwebtoken.Claims
    * @Author: starao
    * @Date: 2022/10/5
    */
    public static Claims getRefreshTokenBody(String refreshToken){
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }
}
