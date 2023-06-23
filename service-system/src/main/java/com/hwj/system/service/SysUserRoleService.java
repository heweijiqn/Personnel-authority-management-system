package com.hwj.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysUserRole;
import com.hwj.model.vo.AssginRoleVo;

import java.util.Map;

public interface SysUserRoleService extends IService<SysUserRole> {





    Map<String, Object> getRolesByUserId(String userId);

    void doAssign(AssginRoleVo assginRoleVo);
}
