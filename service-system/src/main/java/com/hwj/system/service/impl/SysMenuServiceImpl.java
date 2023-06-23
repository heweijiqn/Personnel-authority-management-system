package com.hwj.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.model.system.SysDept;
import com.hwj.model.system.SysMenu;
import com.hwj.model.vo.RouterVo;
import com.hwj.system.exception.HwjException;
import com.hwj.system.mapper.SysMenuMapper;
import com.hwj.system.service.SysMenuService;
import com.hwj.system.utils.MenuHelper;
import com.hwj.system.utils.RouterHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-06-16
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> findMenuTree() {

        //获取全部权限列表
        List<SysMenu> sysMenus = baseMapper.selectList(null);


        List<SysMenu> result = MenuHelper.buildTree(sysMenus);
        return result;
    }

    @Override
    public void removeMeunById(String id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer selectCount = baseMapper.selectCount(wrapper);
        if (selectCount > 0){
            throw new HwjException(201,"请先删除子菜单");
        }
        baseMapper.deleteById(id);
    }

    /*
        * @Author hwj
        * @Description //TODO 根据用户id查询菜单权限
        *                 1.如果是超级管理员，查询所有权限数据
        *                2.如果不是超级管理员，查询这个用户权限
        * @Date  2021/6/21 15:58
     */
    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        //admin是超级管理员，操作所有内容
        List<SysMenu> sysMenuList = null;
        //判断userid值是1代表超级管理员，查询所有权限数据
        if("1".equals(userId)) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value"); //排序 根据sort_value字段升序排序
            sysMenuList = baseMapper.selectList(wrapper);
        } else {
            //如果userid不是1，其他类型用户，查询这个用户权限
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }

        //构建是树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //转换成前端路由要求格式数据
        return RouterHelper.buildRouters(sysMenuTreeList);
    }


    /*
        * @Author hwj
        * @Description //TODO 根据用户id查询按钮权限
        *                 1.如果是超级管理员，查询所有权限数据
        *                2.如果不是超级管理员，查询这个用户权限
        * @Date  2021/6/21 15:58
     */
    @Override
    public List<String> getUserButtonList(String id) {
        List<SysMenu> sysMenuList = null;
        if("1".equals(id)) {
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value"); //排序 根据sort_value字段升序排序
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            sysMenuList = baseMapper.findMenuListUserId(id);
        }
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getType()==2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }

    @Override
    public boolean hasSubDeptWithStatusOne(String id) {
        QueryWrapper<SysMenu> wrapper= new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<SysMenu> sysMenus = baseMapper.selectList(wrapper);
        for (SysMenu sysMenuList :sysMenus ) {
            if (sysMenuList.getStatus() == 1) { // 存在正常状态的子部门
                return true; // 返回true表示存在正常状态的子部门
            }
        }
        return false;
    }

    @Override
    public boolean updateDeptStatus(String id, Integer status) {
        SysMenu menu = baseMapper.selectById(id);
        if (menu != null) {
            menu.setStatus(status);
            int rowsAffected = baseMapper.updateById(menu); // 更新部门状态
            return rowsAffected > 0; // 返回true表示更新成功
        }
        return false;
    }


}
