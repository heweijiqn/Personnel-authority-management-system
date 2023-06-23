package com.hwj.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysDept;
import com.hwj.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-06-20
 */
public interface SysDeptService extends IService<SysDept> {


    List<SysDept> findDeptTree();

    void removeDeptById(String id);

    boolean hasSubDeptWithStatusOne(String id);

    boolean updateDeptStatus(String id, Integer status);
}
