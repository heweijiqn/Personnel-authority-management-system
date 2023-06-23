/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:42
 */

package com.hwj.model.system;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hwj.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "岗位")
@TableName("sys_post")
public class SysPost extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "岗位编码")
	@TableField("post_code")
	private String postCode;

	@ApiModelProperty(value = "岗位名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "显示顺序")
	@TableField("description")
	private String description;

	@ApiModelProperty(value = "状态（1正常 0停用）")
	@TableField("status")
	private Integer status;

}