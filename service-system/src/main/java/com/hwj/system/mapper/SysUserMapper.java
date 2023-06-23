package com.hwj.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwj.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-06-14
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, @Param("vo") SysUserQueryVo sysUserQueryVo);

}
