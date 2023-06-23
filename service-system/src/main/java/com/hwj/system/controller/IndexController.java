/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/14 14:56
 */


package com.hwj.system.controller;


import com.hwj.common.result.Result;
import com.hwj.common.result.ResultCodeEnum;
import com.hwj.common.untils.JwtHelper;
import com.hwj.common.untils.MD5;
import com.hwj.model.system.SysUser;
import com.hwj.model.vo.LoginVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.exception.HwjException;
import com.hwj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "登录管理接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //login


    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());
        if(sysUser == null){
            throw new HwjException(20001,"用户名不存在");
        }
        String password = loginVo.getPassword();
        if(!MD5.encrypt(password).equals(sysUser.getPassword())){
            throw new HwjException(20001,"密码错误");
        }
        if (sysUser.getStatus() == 0){
            throw new HwjException(20001,"用户已被禁用");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }


    //info

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        //获取请求头token字符串
        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        String username = JwtHelper.getUsername(token);

        //根据用户名称获取用户信息（基本信息 和 菜单权限 和 按钮权限数据）
        Map<String,Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }


    @Log(title = "退出登录",businessType = BusinessType.FORCE)
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
