package com.hwj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwj.model.system.SysRole;
import com.hwj.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    void testSelectAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
    }


    @Test
    void testAdd() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("test");
        sysRole.setRoleCode("testCode");
        sysRole.setDescription("testDescription");
        sysRoleMapper.insert(sysRole);
    }

    @Test
    void testUpdate() {
        SysRole sysRole = sysRoleMapper.selectById(1);
        sysRole.setDescription("超级系统管理员");
        int i = sysRoleMapper.updateById(sysRole);
        System.out.println(i);
    }

    @Test
    void testDelete() {
        int i = sysRoleMapper.deleteById(2);
        System.out.println(i);
    }


    @Test
    void testSelectByCondition() {
        QueryWrapper<SysRole> SysRoleQueryWrapper = new QueryWrapper<>();
        SysRoleQueryWrapper.eq("role_name", "超级系统管理员");
        SysRole sysRole = sysRoleMapper.selectOne(SysRoleQueryWrapper);

    }

    @Test
    void testDeleteByCondition() {
        QueryWrapper<SysRole> SysRoleQueryWrapper = new QueryWrapper<>();
        SysRoleQueryWrapper.eq("role_name", "test");
        int i = sysRoleMapper.delete(SysRoleQueryWrapper);
        System.out.println(i);
    }



}