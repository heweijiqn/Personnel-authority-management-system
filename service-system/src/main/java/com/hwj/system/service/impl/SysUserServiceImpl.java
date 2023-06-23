package com.hwj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.model.system.SysUser;
import com.hwj.model.vo.RouterVo;
import com.hwj.model.vo.SysUserQueryVo;
import com.hwj.system.mapper.SysUserMapper;
import com.hwj.system.service.SysMenuService;
import com.hwj.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-06-14
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(pageParam,sysUserQueryVo);
    }

    @Override
    public void updatesStatus(String id, Integer status) {
        //根据id查询用户
        SysUser sysUser = baseMapper.selectById(id);
        //设置状态
        sysUser.setStatus(status);
        //更新
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return baseMapper.selectOne(wrapper);  //selectOne()方法返回的是一个对象，而selectList()方法返回的是一个集合
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //根据username查询用户基本信息
        SysUser sysUser = this.getByUsername(username);
        //根据userid查询菜单权限值
        List<RouterVo> routerVolist = sysMenuService.getUserMenuList(sysUser.getId());
        //根据userid查询按钮权限值
        List<String> permsList = sysMenuService.getUserButtonList(sysUser.getId());

        Map<String,Object> result = new HashMap<>();
        result.put("name",username);
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //菜单权限数据
        result.put("routers",routerVolist);
        //按钮权限数据
        result.put("buttons",permsList);
        return result;
    }


}
