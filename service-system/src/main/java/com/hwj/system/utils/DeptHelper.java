/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/20 21:52
 */


package com.hwj.system.utils;

import com.hwj.model.system.SysDept;

import java.util.ArrayList;
import java.util.List;

public class DeptHelper {
    /*
     * sysDepts是全部数据
     * sysDept是初始化的数据
     * 如果全部数据的parentId=0，就是初始化的数据
     * 递归查询，如果全部数据的parentId=初始化的数据的id，就是子部门
     *
     */
    public static List<SysDept> buildTree(List<SysDept> sysDepts) {
        List<SysDept> result = new ArrayList<>();

        for (SysDept sysDept : sysDepts) {
            if (sysDept.getParentId() == 0) {
                result.add(findChildren(sysDept, sysDepts));
            }
        }
        return result;
    }

    /*
     * 1.初始化数据
     * 从根节点进行递归查询，查询子部门 parentId 是否相同，如果相同是子部门，进行数据封装
     * 2.遍历全部数据
     * 3.比较
     *  sysDept 是初始化的数据，sysDepts 是全部数据，要用初始化的数据去和全部数据比较，如果相同就是子部门，进行数据封装
     * 4.封装
     */
    private static SysDept findChildren(SysDept sysDept, List<SysDept> sysDepts) {
        sysDept.setChildren(new ArrayList<SysDept>()); // 数据初始化

        for (SysDept childDept : sysDepts) {
            if(Long.parseLong(sysDept.getId())==childDept.getParentId()) {
                if (sysDept.getChildren() == null) {
                    sysDept.setChildren(new ArrayList<>());
                }
                sysDept.getChildren().add(findChildren(childDept,sysDepts));
            }
        }

        return sysDept;
    }
}
