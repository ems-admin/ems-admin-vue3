package com.ems.system.controller;

import com.ems.common.constant.SecurityConstants;
import com.ems.common.constant.CaptchaConstants;
import com.ems.common.exception.BadRequestException;
import com.ems.common.utils.JwtUtil;
import com.ems.common.utils.ResultUtil;
import com.ems.common.utils.StringUtil;
import com.ems.config.config.CacheConfig;
import com.ems.config.security.JwtUser;
import com.ems.logs.annotation.Log;
import com.ems.system.entity.SysRole;
import com.ems.system.entity.SysUser;
import com.ems.system.entity.dto.UserDto;
import com.ems.system.service.SysRoleService;
import com.ems.system.service.SysUserService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 14:08
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController extends ResultUtil {

    private final SysUserService userService;

    private final SysRoleService roleService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final CacheConfig cacheConfig;

    @Log("用户登录")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto, HttpServletRequest request){
        try {
            //  校验验证码
            if (StringUtil.isBlank(userDto.getCode()) || !checkCode(userDto.getUuid(), userDto.getCode())){
                return fail(false, "验证码错误");
            }
            //  根据用户名查询用户是否存在
            SysUser user = userService.findByName(userDto.getUsername());
            if (user == null){
                return fail(false, "用户名或密码错误");
            }
            //  判断密码是否正确
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            //  将认证信息设置到SpringSecurity上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //  获取当前用户角色
            List<String> roles = getRolesByUserId(user.getId());
            //  生成token
            String token = JwtUtil.generateToken(user.getUsername(), roles, false);
            //  生成refresh_token
            String refreshToken = JwtUtil.getRefreshToken(user.getUsername());

            //  用户信息
            userDto.setEmail(user.getEmail());
            userDto.setNickName(user.getNickName());
            userDto.setRoles(roles);
            //  隐藏密码
            userDto.setPassword("******");

            return success(true, new JwtUser(token, refreshToken, userDto));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        } catch (Exception e){
            e.printStackTrace();
            return fail(false, e.getMessage());
        }
    }

    /**
    * @Description: 用户注册
    * @Param: [userDto]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2021/11/27
    */
    @Log("用户注册")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto){
        try {
            userService.editUser(userDto);
            return success(true, "注册成功");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }
    
    /**
    * @Description: 通过refreshToken得到新的token
    * @Param: [request]
    * @return: org.springframework.http.ResponseEntity<java.lang.Object>
    * @Author: starao
    * @Date: 2022/10/5
    */
    @PutMapping("/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request){
        try {
            //  通过请求头获取refreshToken
            String refreshToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
            // 如果refreshToken存在
            if (StringUtil.isNotBlank(refreshToken)){
                //  去掉头部，得到原始refreshToken
                refreshToken = refreshToken.replaceFirst(SecurityConstants.TOKEN_PREFIX, "");
                //  解析refreshToken,得到用户名
                Claims claims = JwtUtil.getRefreshTokenBody(refreshToken);
                System.out.println(claims.getSubject());
                //  如果refreshToken还在有效期内
                if (claims.get("exp", Long.class) > 0){
                    //  获取当前用户信息
                    SysUser user = userService.findByName(claims.getSubject());
                    //  获取当前用户角色
                    List<String> roles = getRolesByUserId(user.getId());
                    //  重新获取token
                    String token = JwtUtil.generateToken(user.getUsername(), roles, false);
                    return success(true, token);
                }
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
        return fail(false, "请重新登录");
    }

    /**
     * @Description: 获取验证码
     * @Param: []
     * @return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: starao
     * @Date: 2022/1/18
     */
    @GetMapping("/code")
    public ResponseEntity<Object> getVerifyCode(){
        try {
            // 获取运算的结果
            Captcha captcha = new ArithmeticCaptcha(CaptchaConstants.width, CaptchaConstants.height);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
            String captchaValue = captcha.text();
            if (captchaValue.contains(".")) {
                captchaValue = captchaValue.split("\\.")[0];
            }
            // 缓存验证码信息,时间1分钟
            cacheConfig.put(uuid, captchaValue, 1);
            // 验证码信息
            Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
                put("img", captcha.toBase64());
                put("uuid", uuid);
            }};
            return ResponseEntity.ok(imgResult);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
    * @Description: 获取当前用户角色列表
    * @Param: [userId]
    * @return: java.util.List<java.lang.String>
    * @Author: starao
    * @Date: 2022/10/5
    */
    private List<String> getRolesByUserId(Long userId){
        //  获取当前用户角色对象
        List<SysRole> sysRoles = roleService.getRoleByUserId(userId);
        //  角色
        List<String> roles = new ArrayList<>();
        sysRoles.forEach( sysRole -> {
            roles.add(sysRole.getRoleCode());
        });
        return roles;
    }

    /**
     * @Description: 验证码校验
     * @Param: [uuid, code]
     * @return: boolean
     * @Author: starao
     * @Date: 2023/7/9
     */
    private boolean checkCode(String uuid, String code){
        boolean b = false;
        if (cacheConfig.get(uuid) != null && cacheConfig.get(uuid).equals(code)){
            b = true;
            cacheConfig.invalidate(uuid);
        }
        return b;
    }
}
