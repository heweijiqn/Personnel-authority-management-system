package com.hwj.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwj.model.system.SysDept;
import com.hwj.system.exception.HwjException;
import com.hwj.system.mapper.SysDeptMapper;
import com.hwj.system.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.system.utils.DeptHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-06-20
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> findDeptTree() {
        List<SysDept> sysDepts = baseMapper.selectList(null);
        List<SysDept> result = DeptHelper.buildTree(sysDepts);
        return result;
    }
    /*
        * @Author hwj
        * @Description //TODO 根据id删除部门
        * @Date 2021/6/20 22:04
        * @Param [id]
        * @return void
     */
    @Override
    public void removeDeptById(String id) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer selectCount = baseMapper.selectCount(wrapper);
        if (selectCount > 0){
            throw new HwjException(201,"该部门下有子部门，无法删除");
        }
        baseMapper.deleteById(id);
    }
    /*
        * @Author hwj
        * @Description //TODO 根据id查询部门
        * @Date 2021/6/20 22:04
        * @Param [id]
        * @return com.hwj.model.system.SysDept
     */
    @Override
    public boolean hasSubDeptWithStatusOne(String id) {
        QueryWrapper<SysDept> wrapper= new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<SysDept> subDepts = baseMapper.selectList(wrapper);
        for (SysDept subDept : subDepts) {
            if (subDept.getStatus() == 1) {
                return true;
            }
        }
        return false;
    }

    /*
        * @Author hwj
        * @Description //TODO 根据id修改部门状态
        * @Date 2021/6/20 22:04
        * @Param [id, status]
        * @return boolean
     */
    @Override
    public boolean updateDeptStatus(String id, Integer status) {
        SysDept dept = baseMapper.selectById(id);
        if (dept != null) {
            dept.setStatus(status);
            int rowsAffected = baseMapper.updateById(dept); // 更新部门状态
            return rowsAffected > 0; // 返回true表示更新成功
        }
        return false;
    }


}
