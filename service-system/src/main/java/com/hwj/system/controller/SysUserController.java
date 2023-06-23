package com.hwj.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.common.result.Result;
import com.hwj.common.untils.MD5;
import com.hwj.model.system.SysUser;
import com.hwj.model.vo.SysUserQueryVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-06-14
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;



    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation("用户列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam,sysUserQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("/getUser/{id}")
    public Result get(@PathVariable String id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }


    @Log(title = "用户管理",businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user) {
        String encrypt = MD5.encrypt(user.getPassword());
        user.setPassword(encrypt);
        boolean save = sysUserService.save(user);
        if (save) {
            return Result.ok();
        }else {
            return Result.fail();
        }

    }


    @Log(title = "用户管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新用户")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysUser user) {
        boolean update = sysUserService.updateById(user);
        if (update) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


    @Log(title = "用户管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        boolean removeById = sysUserService.removeById(id);
       if (removeById) {
           return Result.ok();
         }else {
              return Result.fail();
       }
    }

    @Log(title = "用户管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysUser.updateStatus')")
    @ApiOperation(value = "修改用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,
                               @PathVariable Integer status) {
       sysUserService.updatesStatus(id,status);
       return Result.ok();
    }


}

