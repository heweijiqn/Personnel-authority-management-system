/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/22 17:58
 */


package com.hwj.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwj.common.result.Result;
import com.hwj.model.system.SysOperLog;
import com.hwj.model.vo.SysOperLogQueryVo;
import com.hwj.system.annotation.Log;
import com.hwj.system.enums.BusinessType;
import com.hwj.system.service.OperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "操作日志管理接口")
@RestController
@RequestMapping(value="/admin/system/sysOperLog")
public class SysOperLogController {

    @Autowired
    private OperLogService operLogService;


    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
            @PathVariable Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        IPage<SysOperLog> pageModel = operLogService.selectPage(page,limit,sysOperLogQueryVo);
        return Result.ok(pageModel);
    }

    @Log(title = "操作日志管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysOperLog.delete')")
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        operLogService.removeById(id);
        return Result.ok();
    }

    //批量删除操作日志

    @Log(title = "操作日志管理",businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysOperLog.batchRemove')")
    @ApiOperation(value = "批量删除操作日志")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        operLogService.removeByIds(idList);
        return Result.ok();
    }
}
