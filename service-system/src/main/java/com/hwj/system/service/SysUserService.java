package com.hwj.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysUser;
import com.hwj.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-06-14
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);


    void updatesStatus(String id, Integer status);

    SysUser getByUsername(String username);

    Map<String, Object> getUserInfo(String username);
}
