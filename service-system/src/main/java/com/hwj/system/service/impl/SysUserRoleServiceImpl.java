/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/15 22:16
 */


package com.hwj.system.service.impl;




import com.hwj.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.model.system.SysUserRole;
import com.hwj.model.vo.AssginRoleVo;
import com.hwj.system.mapper.SysRoleMapper;
import com.hwj.system.mapper.SysUserRoleMapper;
import com.hwj.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;




    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        //获取所有角色
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        //根据用户id查询，已经分配角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRolesList = baseMapper.selectList(wrapper);
        //从userRoles集合获取所有角色id
        List<String> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole:userRolesList) {
            String roleId = userRole.getRoleId();
            userRoleIds.add(roleId);
        }
        //封装到map集合
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",sysRoles);//所有角色
        returnMap.put("userRoleIds",userRoleIds);//用户分配角色id集合
        return returnMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除之前分配角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        baseMapper.delete(wrapper);
        //获取所有角色id，添加角色用户关系表
        List<String> roleIdList = assginRoleVo.getRoleIdList(); //角色id列表
        for (String roleId:roleIdList) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            baseMapper.insert(userRole);
        }
    }
}
