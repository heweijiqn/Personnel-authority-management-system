/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:51
 */
package com.hwj.model.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户查询实体
 */
@Data
public class SysUserQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String keyword;

	private String createTimeBegin;
	private String createTimeEnd;

	private Long roleId;
	private Long postId;
	private Long deptId;

}

