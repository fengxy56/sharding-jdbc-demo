package com.fxy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 移动审批表
 *
 * @author fengxiaoyang
 * @date 2021-7-2 15:29:16
 */
@Data
@Accessors(chain = true)
@ApiModel("移动审批表")
@TableName("hk_push")
public class HkPush implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

  	@ApiModelProperty(value = "应用id（防止调用错误）" )
	private String appId;

  	@ApiModelProperty(value = "业务消息id" )
	private String requestId;

  	@ApiModelProperty(value = "编码" )
	private String processCode;

  	@ApiModelProperty(value = "当前操作用户ID" )
	private String userId;

  	@ApiModelProperty(value = "是否待办，1是，0否" )
	private Integer ifTodo;

  	@ApiModelProperty(value = "审批标题" )
	private String msgTitle;

  	@ApiModelProperty(value = "审批跳转路径" )
	private String ptUrl;

  	@ApiModelProperty(value = "表单详情" )
	private String formInfoContent;

  	@ApiModelProperty(value = "鸿信侧消息id" )
	private String hxMsgid;

  	@ApiModelProperty(value = "是否点击(1:未点击；0：已点击)" )
	private Integer clickStatus;

  	@ApiModelProperty(value = "待办接收时间" )
	private Date receiveTime;

  	@ApiModelProperty(value = "是否抄送Y是,N不是抄送," )
	private String isCc;

  	@ApiModelProperty(value = "发起人账户" )
	private String authorUser;

  	@ApiModelProperty(value = "是否发起人true是，false否" )
	private String ifAuthor;

  	@ApiModelProperty(value = "null" )
	private Date finishTime;


}
