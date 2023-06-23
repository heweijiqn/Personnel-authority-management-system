/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/15 22:28
 */


package com.hwj.system.controller;


import com.hwj.common.result.Result;
import com.hwj.model.vo.AssginRoleVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "用户角色管理接口")
@RestController
@RequestMapping("/admin/system/sysUserRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;



    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = sysUserRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }


    @Log(title = "用户角色管理",businessType = BusinessType.IMPORT)
    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysUserRoleService.doAssign(assginRoleVo);
        return Result.ok();

    }
}
