/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/22 16:31
 */


package com.hwj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysLoginLog;
import com.hwj.model.vo.SysLoginLogQueryVo;
import com.hwj.system.mapper.LonginLogMapper;
import com.hwj.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LonginLogMapper longinLogMapper;

    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {

        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        longinLogMapper.insert(sysLoginLog);

    }

    @Override
    public IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo) {
        //创建page对象
        Page<SysLoginLog> pageParam = new Page(page,limit);

        //获取条件值
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();

        //封装条件
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(username)) {
            wrapper.like("username",username); //模糊查询
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin); //大于等于
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.le("create_time",createTimeEnd);  //小于等于
        }
        IPage<SysLoginLog> pageModel  = longinLogMapper.selectPage(pageParam, wrapper);
        return pageModel;
    }

    /*
    删除登录日志
    根据id删除
     */
    @Override
    public void removeById(String id) {
        longinLogMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<Long> idList) {
        longinLogMapper.deleteBatchIds(idList);

    }


}
