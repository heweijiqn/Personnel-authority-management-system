/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/18 23:05
 */


package com.hwj.system.fillter;

import com.alibaba.fastjson.JSON;
import com.hwj.common.result.Result;
import com.hwj.common.result.ResultCodeEnum;
import com.hwj.common.untils.JwtHelper;
import com.hwj.common.untils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token"); // 从请求头中获取token
        logger.info("token:"+token);   // 打印token
        if (!StringUtils.isEmpty(token)) {  // 如果token不为空
            String username = JwtHelper.getUsername(token);  // 从token中获取用户名
            logger.info("useruame:"+username); // 打印用户名
            if (!StringUtils.isEmpty(username)) {  // 如果用户名不为空
                String authoritiesString = (String) redisTemplate.opsForValue().get(username);  // 从redis中获取权限
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);  // 将权限转换为map
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();  // 创建权限列表
                for (Map map : mapList) {
                    authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
                }
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }
        return null;
    }
}
