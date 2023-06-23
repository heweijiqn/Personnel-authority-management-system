package com.hwj.system.controller;


import com.hwj.common.result.Result;
import com.hwj.model.system.SysMenu;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-06-16
 */

@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation("获取菜单数据")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findMenuTree();
        return Result.ok(list);
    }


    @Log(title = "菜单管理",businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysMenu.add')")
    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public  Result save(@RequestBody SysMenu sysMenu){
        boolean save = sysMenuService.save(sysMenu);
        if (save) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id查询菜单")
    @GetMapping("/findNode/{id}")
    public  Result getById(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }


    @Log(title = "菜单管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation("修改菜单")
    @PostMapping("/update")
    public  Result updateById(@RequestBody SysMenu sysMenu){
        boolean updateById = sysMenuService.updateById(sysMenu);
      if (updateById) {
          return Result.ok();
        }else {
            return Result.fail();
      }
    }

    @Log(title = "菜单管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    @ApiOperation("删除菜单")
    @DeleteMapping("/remove/{id}")
    public  Result removeById(@PathVariable String id){
         sysMenuService.removeMeunById(id);
        return Result.ok();

    }

    @Log(title = "菜单管理",businessType = BusinessType.STATUS)
    @PreAuthorize("hasAuthority('bnt.sysDept.updateStatus')")
    @ApiOperation("修改部门状态")
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,  @PathVariable Integer status) {
        // 检查是否存在下属部门且状态为1
        boolean hasSubDeptWithStatusOne = sysMenuService.hasSubDeptWithStatusOne(id);
        if (hasSubDeptWithStatusOne) {
            return Result.fail("存在下属菜单且状态为1，无法修改菜单状态");
        }

        boolean updateStatus = sysMenuService.updateDeptStatus(id, status);
        if (updateStatus) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }



}

