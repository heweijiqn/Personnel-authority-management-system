/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/12 21:15
 */


package com.hwj.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.hwj.model.vo.SysRoleQueryVo;
import com.hwj.system.mapper.SysRoleMapper;

import com.hwj.system.service.SysRoleService;
import com.hwj.model.system.SysRole;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return pageModel;
    }


}
