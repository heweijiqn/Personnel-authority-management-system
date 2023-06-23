package com.hwj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysOperLog;
import com.hwj.model.vo.SysOperLogQueryVo;
import com.hwj.system.mapper.OperLogMapper;
import com.hwj.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public void saveSysLog(SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }


    //操作日志分页查询
    @Override
    public IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page,limit);
        //获取条件值
        String title = sysOperLogQueryVo.getTitle();
        String operName = sysOperLogQueryVo.getOperName();
        String createTimeBegin = sysOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysOperLogQueryVo.getCreateTimeEnd();
        //封装参数
        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(operName)) {
            wrapper.like("oper_name",operName);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper方法实现分页条件查询
        IPage<SysOperLog> sysOperLogPage = operLogMapper.selectPage(pageParam, wrapper);
        return sysOperLogPage;
    }

    @Override
    public void removeByIds(List<Long> idList) {
        operLogMapper.deleteBatchIds(idList);
    }

    @Override
    public void removeById(String id) {
        operLogMapper.deleteById(id);

    }


}
