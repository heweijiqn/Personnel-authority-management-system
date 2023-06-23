/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:48
 */

package com.hwj.model.vo;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SysLoginLogQueryVo {
	
	@ApiModelProperty(value = "用户账号")
	private String username;


	private String createTimeBegin;
	private String createTimeEnd;

}

