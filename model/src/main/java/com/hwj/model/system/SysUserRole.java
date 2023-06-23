/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:45
 */
package com.hwj.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hwj.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户角色")
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private String roleId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private String userId;
}

