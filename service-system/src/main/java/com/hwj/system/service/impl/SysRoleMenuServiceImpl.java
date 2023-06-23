/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/16 16:51
 */


package com.hwj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.model.system.SysMenu;
import com.hwj.model.system.SysRoleMenu;
import com.hwj.model.vo.AssginMenuVo;
import com.hwj.system.mapper.SysMenuMapper;
import com.hwj.system.mapper.SysRoleMenuMapper;
import com.hwj.system.service.SysRoleMenuService;
import com.hwj.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        //获取所有菜单 status=1
        QueryWrapper<SysMenu> wrapperMenu = new QueryWrapper<>(); //创建条件对象
        wrapperMenu.eq("status",1);  //设置条件
        List<SysMenu> menuList = sysMenuMapper.selectList(wrapperMenu); //查询所有菜单

        //根据角色id查询 角色分配过的菜单列表
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<SysRoleMenu> roleMenus = baseMapper.selectList(wrapperRoleMenu); //查询角色分配过的菜单列表

        //从第二步查询列表中，获取角色分配所有菜单id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu:roleMenus) {
            String menuId = sysRoleMenu.getMenuId();
            roleMenuIds.add(menuId);
        }

        //数据处理：isSelect 如果菜单选中 true，否则false
        // 拿着分配菜单id 和 所有菜单比对，有相同的，让isSelect值true
        for (SysMenu sysMenu:menuList) {
            if(roleMenuIds.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }

        //转换成树形结构为了最终显示 MenuHelper方法实现
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        baseMapper.delete(wrapper);
        //遍历菜单id列表，一个一个进行添加
        for (String menuId:assginMenuVo.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            baseMapper.insert(sysRoleMenu);
        }

    }
}
