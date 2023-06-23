/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:44
 */

package com.hwj.model.system;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hwj.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "角色菜单")
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private String roleId;

	@ApiModelProperty(value = "菜单id")
	@TableField("menu_id")
	private String menuId;

}

