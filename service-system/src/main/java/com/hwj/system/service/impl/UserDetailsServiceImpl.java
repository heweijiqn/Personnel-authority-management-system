package com.hwj.system.service.impl;


import com.hwj.model.system.SysUser;
import com.hwj.system.custom.CustomUser;
import com.hwj.system.service.SysMenuService;
import com.hwj.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/*
* 1.根据用户名查询用户信息
* 2.根据用户id查询用户权限
* 实现UserDetailsService接口，重写loadUserByUsername方法
* springSecurity会调用loadUserByUsername方法，根据用户名查询用户信息
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("用户被禁用了");
        }
        //根据userid查询操作权限数据
        List<String> userPermsList = sysMenuService.getUserButtonList(sysUser.getId());
        //转换security要求格式数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm:userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}
