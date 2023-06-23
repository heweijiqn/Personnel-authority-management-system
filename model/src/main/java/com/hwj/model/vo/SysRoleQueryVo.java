/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:50
 */
package com.hwj.model.vo;

import java.io.Serializable;

/**
 * 角色查询实体
 *
 */
public class SysRoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

