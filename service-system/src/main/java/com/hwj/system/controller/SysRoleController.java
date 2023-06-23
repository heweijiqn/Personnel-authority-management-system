/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/12 21:33
 */


package com.hwj.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.common.result.Result;
import com.hwj.model.system.SysRole;

import com.hwj.model.vo.SysRoleQueryVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation(value = "查询全部角色")
    @GetMapping("/findAll")
    public Result findAll() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);
    }


    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        boolean IsSuccess = sysRoleService.removeById(id);
        if (IsSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "角色管理", businessType = BusinessType.OTHER)
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo) {
        //创建page对象
        Page<SysRole> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        //返回
        return Result.ok(pageModel);
    }

    /*@RequestBody 不能使用get提交方式
        传递json格式数据，把json格式数据封装到对象里面 {...}
        传递表单格式数据，把表单里面的数据封装到对象里面
     **/


    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result saveRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.save(sysRole);
        if(isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @ApiOperation("根据id查询角色")
    @PostMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable("id") String id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PostMapping("/update")
    public Result updateRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if(isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
    /*批量删除
    *多个id值 [1,2,3]
    *json数组格式 --- java的list集合
     **/


    @Log(title = "角色管理", businessType = BusinessType.CLEAN)
    @PreAuthorize("hasAuthority('bnt.sysRole.batchRemove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean removeByIds = sysRoleService.removeByIds(idList);
        if (removeByIds) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


}
