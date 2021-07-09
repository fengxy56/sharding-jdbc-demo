package com.fxy.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 移动审批表
 *
 * @author fengxiaoyang
 * @date 2020-5-20 9:04:48
 */
@Data
@ApiModel("移动审批表")
@TableName("hongkun_pushinfo")
public class HongkunPushinfo implements Serializable {

	@TableField(value = "id")
	@ApiModelProperty(value = "主键id" )
	private Integer id;

	@TableField(value = "appId")
	@ApiModelProperty(value = "应用id（防止调用错误）" )
	private String appId;

	@TableField(value = "processCode")
	@ApiModelProperty(value = "编码" )
	private String processCode;

	@TableField(value = "requestId")
	@ApiModelProperty(value = "业务消息id" )
	private String requestId;

	@TableField(value = "userId")
	@ApiModelProperty(value = "当前操作用户ID" )
	private String userId;

	@TableField(value = "ifTodo")
	@ApiModelProperty(value = "是否待办，1是，0否" )
	private Integer ifTodo;

	@TableField(value = "msgTitle")
	@ApiModelProperty(value = "审批标题" )
	private String msgTitle;

	@TableField(value = "ptUrl")
	@ApiModelProperty(value = "审批跳转路径" )
	private String ptUrl;

	@TableField(value = "formInfo_content")
	@ApiModelProperty(value = "表单详情" )
	private String formInfoContent;


	@TableField(value = "hx_msgid")
	@ApiModelProperty(value = "鸿信侧消息id" )
	private String hxMsgid;

	@TableField(value = "click_status")
	@ApiModelProperty(value = "是否点击(1:未点击；0：已点击)" )
	private Integer clickStatus;

	@TableField(value = "receiveTime")
	@ApiModelProperty(value = "待办接收时间" )
	private Date receiveTime;

	@TableField(value = "isCc")
	@ApiModelProperty(value = "是否抄送Y是,N不是抄送," )
	private String isCc;

	@TableField(value = "authorUser")
	@ApiModelProperty(value = "发起人账户" )
	private String authorUser;

	@TableField(value = "ifAuthor")
	@ApiModelProperty(value = "是否发起人true是，false否" )
	private String ifAuthor;


}
