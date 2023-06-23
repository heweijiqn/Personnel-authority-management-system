/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:47
 */

package com.hwj.model.vo;

import lombok.Data;

/**
 * 路由显示信息
 *
 */
@Data
public class MetaVo
{
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

}

