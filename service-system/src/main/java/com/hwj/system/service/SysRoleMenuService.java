package com.hwj.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysMenu;
import com.hwj.model.system.SysRoleMenu;
import com.hwj.model.vo.AssginMenuVo;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    List<SysMenu> findMenuByRoleId(String roleId);

    void doAssign(AssginMenuVo assginMenuVo);
}
