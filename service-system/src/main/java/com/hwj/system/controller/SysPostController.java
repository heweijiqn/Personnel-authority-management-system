package com.hwj.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.common.result.Result;
import com.hwj.model.system.SysPost;
import com.hwj.model.vo.SysPostQueryVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.SysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-06-21
 */

@Api(tags = "岗位管理接口")
@RestController
@RequestMapping("/admin/system/sysPost")
public class SysPostController {


    @Autowired
    private SysPostService sysPostService;

    @Log(title = "岗位管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysPost.remove')")
    @ApiOperation("逻辑删除")
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable String id) {
        boolean IsSuccess = sysPostService.removeById(id);
        if (IsSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @PreAuthorize("hasAuthority('bnt.sysPost.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysPostQueryVo sysPostQueryVo) {
        //创建page对象
        Page<SysPost> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysPost> pageModel = sysPostService.selectPage(pageParam,sysPostQueryVo);
        //返回
        return Result.ok(pageModel);
    }

    /*@RequestBody 不能使用get提交方式
        传递json格式数据，把json格式数据封装到对象里面 {...}
        传递表单格式数据，把表单里面的数据封装到对象里面
     **/

    @Log(title = "岗位管理",businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysPost.add')")
    @ApiOperation("添加岗位")
    @PostMapping("/save")
    public Result saveRole(@RequestBody SysPost sysPost) {
        boolean isSuccess = sysPostService.save(sysPost);
        if(isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @ApiOperation("根据id查询岗位")
    @PostMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable("id") String id) {
        SysPost sysPost = sysPostService.getById(id);
        return Result.ok(sysPost);
    }


    @Log(title = "岗位管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysPost.update')")
    @ApiOperation("修改岗位")
    @PostMapping("/update")
    public Result updateRole(@RequestBody SysPost sysRole) {
        boolean isSuccess = sysPostService.updateById(sysRole);
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


    @Log(title = "岗位管理",businessType = BusinessType.CLEAN)
    @PreAuthorize("hasAuthority('bnt.sysPost.batchRemove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean removeByIds = sysPostService.removeByIds(idList);
        if (removeByIds) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @Log(title = "岗位管理",businessType = BusinessType.STATUS)
    @PreAuthorize("hasAuthority('bnt.sysPost.updateStatus')")
    @ApiOperation(value = "修改岗位状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,
                               @PathVariable Integer status) {
        sysPostService.updateStatus(id,status);
        return Result.ok();
    }

}

