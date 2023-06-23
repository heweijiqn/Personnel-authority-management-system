/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/16 14:57
 */


package com.hwj.system.utils;

import com.hwj.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    /*
    * sysMenus是全部数据
    * sysMenu是初始化的数据
    * 如果全部数据的parentid=0，就是初始化的数据
    * 递归查询，如果全部数据的parentid=初始化的数据的id，就是子节点
    *
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenus) {
        List<SysMenu> result = new ArrayList<>();

        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId() == 0) {
                result.add(findChildren(sysMenu, sysMenus));
            }
        }
        return result;
    }



    /*
    * 1.初始化数据
    * 从根节点进行递归查询，查询子节点 parentid是否相同，如果相同是子节点，进行数据封装
    * 2.遍历全部数据
    * 3.比较
    *  sysMenu是初始化的数据，sysMenus是全部数据 要用初始化的数据去和全部数据比较，如果相同就是子节点，进行数据封装
    * 4.封装
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenus) {

        //数据初始化
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //遍历递归查找
        for (SysMenu ids : sysMenus) {
            //获取当前菜单id
            if(Long.parseLong(sysMenu.getId())==ids.getParentId()) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(ids,sysMenus));
            }


        }
        return sysMenu;
    }

}
