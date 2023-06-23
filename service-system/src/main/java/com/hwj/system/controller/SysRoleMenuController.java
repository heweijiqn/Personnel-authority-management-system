/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/16 16:41
 */


package com.hwj.system.controller;


import com.hwj.common.result.Result;
import com.hwj.model.system.SysMenu;
import com.hwj.model.vo.AssginMenuVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "角色菜单分配接口")
@RestController
@RequestMapping("/admin/system/sysRoleMenuAllot")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @Log(title = "角色菜单分配",businessType = BusinessType.OTHER)
    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<SysMenu> list = sysRoleMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);
    }


    @Log(title = "角色菜单分配",businessType = BusinessType.IMPORT)
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {

        sysRoleMenuService.doAssign(assginMenuVo);

        return Result.ok();
    }

}
