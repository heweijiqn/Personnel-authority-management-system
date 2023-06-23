/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/22 17:44
 */


package com.hwj.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwj.common.result.Result;
import com.hwj.model.system.SysLoginLog;
import com.hwj.model.vo.SysLoginLogQueryVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "登录日志管理接口")
@RestController
@RequestMapping(value="/admin/system/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    private LoginLogService loginLogService;


    //条件分页查询登录日志
    @PreAuthorize("hasAuthority('bnt.sysLoginLog.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable long page,
                        @PathVariable long limit,
                        SysLoginLogQueryVo sysLoginLogQueryVo) {
        IPage<SysLoginLog> pageModel =  loginLogService.selectPage(page,limit,sysLoginLogQueryVo);
        return Result.ok(pageModel);
    }

    //删除登录日志
    @Log(title = "登录日志管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysLoginLog.delete')")
    @ApiOperation(value = "删除登录日志")
    @DeleteMapping ("remove/{id}")
    public Result remove(@PathVariable String id) {
        loginLogService.removeById(id);
        return Result.ok();
    }


    //批量删除登录日志

    @Log(title = "登录日志管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysLoginLog.batchRemove')")
    @ApiOperation(value = "批量删除登录日志")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        loginLogService.removeByIds(idList);
        return Result.ok();
    }
}
