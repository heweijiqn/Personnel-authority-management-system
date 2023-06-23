/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/18 22:26
 */


package com.hwj.system.fillter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwj.common.result.Result;
import com.hwj.common.result.ResultCodeEnum;
import com.hwj.common.untils.JwtHelper;
import com.hwj.common.untils.ResponseUtil;
import com.hwj.model.vo.LoginVo;
import com.hwj.system.custom.CustomUser;
import com.hwj.system.service.LoginLogService;
import com.hwj.common.untils.IpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private RedisTemplate redisTemplate; //redisTemplate 用于存储token

    private LoginLogService loginLogService;
    /*
        * @title: 登录过滤器
        * @description: 登录过滤器
        * 构造器中指定登录接口及提交方式，可以指定任意路径
     */
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate,
                            LoginLogService loginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
        this.loginLogService = loginLogService;
    }

    /*
        * @title: 获取用户名和密码，认证
        * @description: 获取用户名和密码，认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo =
                    new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
        * @title: 认证成功调用
        * @description: 认证成功调用
     */
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        //获取认证对象
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));
        //生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(),
                                            customUser.getSysUser().getUsername());

        //记录登录日志
        loginLogService.recordLoginLog(customUser.getSysUser().getUsername(),1,
                IpUtil.getIpAddress(request),"登录成功");

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /*
        * @title: 认证失败调用
        * @description: 认证失败调用
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        if(e.getCause() instanceof RuntimeException) { //自定义异常
            ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }

}
