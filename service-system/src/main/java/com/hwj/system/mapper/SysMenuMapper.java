package com.hwj.system.mapper;

import com.hwj.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-06-16
 */

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /*
        * @Author hwj
        * @Description //TODO 根据用户id查询菜单权限
        *                 SELECT
        *                     m.*
     */
    List<SysMenu> findMenuListUserId(@Param("userId") String userId);
}
