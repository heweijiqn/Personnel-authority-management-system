package com.hwj.system.controller;


import com.hwj.common.result.Result;
import com.hwj.model.system.SysDept;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-06-20
 */

@Api(tags = "部门管理接口")
@RestController
@RequestMapping("/admin/system/sysDept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;


    @PreAuthorize("hasAuthority('bnt.sysDept.list')")
    @ApiOperation("获取部门数据")
    @GetMapping("/findDept")
    public Result findNodes() {
        List<SysDept> list = sysDeptService.findDeptTree();
        return Result.ok(list);
    }


    @Log(title = "部门管理",businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysDept.add')")
    @ApiOperation("添加部门")
    @PostMapping("/save")
    public  Result save(@RequestBody SysDept sysDept){
        boolean save = sysDeptService.save(sysDept);
        if (save) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id查询部门")
    @GetMapping("/findNode/{id}")
    public  Result getById(@PathVariable String id){
        SysDept sysDept= sysDeptService.getById(id);
        return Result.ok(sysDept);
    }


    @Log(title = "部门管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysDept.update')")
    @ApiOperation("修改部门")
    @PostMapping("/update")
    public  Result updateById(@RequestBody SysDept sysDept){
        boolean updateById = sysDeptService.updateById(sysDept);
        if (updateById) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


    @Log(title = "部门管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysDept.remove')")
    @ApiOperation("删除部门")
    @DeleteMapping("/remove/{id}")
    public  Result removeById(@PathVariable String id){
        sysDeptService.removeDeptById(id);
        return Result.ok();

    }

    @Log(title = "部门管理",businessType = BusinessType.STATUS)
    @PreAuthorize("hasAuthority('bnt.sysDept.updateStatus')")
    @ApiOperation("修改部门状态")
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,  @PathVariable Integer status) {
        // 检查是否存在下属部门且状态为1
        boolean hasSubDeptWithStatusOne = sysDeptService.hasSubDeptWithStatusOne(id);
        if (hasSubDeptWithStatusOne) {
            return Result.fail("存在下属部门且状态为1，无法修改部门状态");
        }

        boolean updateStatus = sysDeptService.updateDeptStatus(id, status);
        if (updateStatus) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


}

