package com.hwj.system.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwj.model.system.SysOperLog;
import com.hwj.model.vo.SysOperLogQueryVo;

import java.util.List;

public interface OperLogService {

    public void saveSysLog(SysOperLog sysOperLog);


    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);

    void removeByIds(List<Long> idList);

    void removeById(String id);
}
