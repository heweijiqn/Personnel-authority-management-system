/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:43
 */

package com.hwj.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hwj.model.base.BaseEntity;
import lombok.Data;


@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@TableField("role_name")
	private String roleName;

	@TableField("role_code")
	private String roleCode;

	@TableField("description")
	private String description;

}

