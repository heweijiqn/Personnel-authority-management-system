package com.hwj.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwj.model.system.SysLoginLog;
import com.hwj.model.vo.SysLoginLogQueryVo;

import java.util.List;

public interface LoginLogService  {

    /*
    记录登录日志
     */
    public void recordLoginLog(String username,Integer status,
                               String ipaddr,String message);


    IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo);

    void removeById(String id);

    void removeByIds(List<Long> idList);
}
