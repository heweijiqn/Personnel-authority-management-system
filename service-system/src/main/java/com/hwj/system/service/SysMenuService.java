package com.hwj.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.model.system.SysMenu;
import com.hwj.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-06-16
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenuTree();

    void removeMeunById(String id);


    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);

    boolean hasSubDeptWithStatusOne(String id);

    boolean updateDeptStatus(String id, Integer status);
}
