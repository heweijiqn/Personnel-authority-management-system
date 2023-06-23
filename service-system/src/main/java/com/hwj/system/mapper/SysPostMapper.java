package com.hwj.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwj.model.vo.SysPostQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 岗位信息表 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-06-21
 */

@Repository
public interface SysPostMapper extends BaseMapper<SysPost> {

    IPage<SysPost> selectPage(Page<SysPost> pageParam, @Param("vo")SysPostQueryVo sysPostQueryVo);

}
