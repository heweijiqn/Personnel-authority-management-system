package com.hwj.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.model.system.SysPost;
import com.hwj.model.vo.SysPostQueryVo;
import com.hwj.system.mapper.SysPostMapper;
import com.hwj.system.service.SysPostService;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-06-21
 */
@Service
@Transactional
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {


    @Override
    public IPage<SysPost> selectPage(Page<SysPost> pageParam, SysPostQueryVo sysPostQueryVo) {
        IPage<SysPost> pageModel = baseMapper.selectPage(pageParam,sysPostQueryVo);
        return pageModel;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        SysPost sysPost = baseMapper.selectById(id);
        sysPost.setStatus(status);
        baseMapper.updateById(sysPost);
    }
}
