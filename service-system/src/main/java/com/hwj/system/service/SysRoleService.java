package com.hwj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysRole;
import com.hwj.model.vo.SysRoleQueryVo;

public interface SysRoleService extends IService<SysRole> {

    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);


}
