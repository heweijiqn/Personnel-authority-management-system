package com.hwj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysRole;
import com.hwj.model.vo.SysPostQueryVo;
import com.hwj.model.vo.SysRoleQueryVo;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-06-21
 */
public interface SysPostService extends IService<SysPost> {


    IPage<SysPost> selectPage(Page<SysPost> pageParam, SysPostQueryVo sysPostQueryVo);

    void updateStatus(String id, Integer status);
}
