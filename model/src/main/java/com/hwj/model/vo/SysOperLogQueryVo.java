/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:48
 */

package com.hwj.model.vo;

import lombok.Data;

@Data
public class SysOperLogQueryVo {

	private String title;
	private String operName;

	private String createTimeBegin;
	private String createTimeEnd;

}

