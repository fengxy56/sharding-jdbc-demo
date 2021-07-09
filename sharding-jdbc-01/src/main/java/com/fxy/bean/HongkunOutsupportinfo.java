package com.fxy.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 移动审批接入应用
 *
 * @author fengxiaoyang
 * @date 2020-5-20 9:08:12
 */
@Data
@ApiModel("移动审批接入应用")
@TableName("hongkun_outsupportinfo")
public class HongkunOutsupportinfo implements Serializable {

	@TableField(value = "id")
  	@ApiModelProperty(value = "主键id" )
	private Integer id;

	@TableField(value = "join_appId")
  	@ApiModelProperty(value = "接入系统应用Id" )
	private String joinAppId;

	@TableField(value = "join_appName")
  	@ApiModelProperty(value = "接入系统应用名称" )
	private String joinAppName;

	@TableField(value = "join_userName")
	@ApiModelProperty(value = "接入系统用户名（验证用）" )
	private String joinUserName;

	@TableField(value = "join_userPassword")
  	@ApiModelProperty(value = "接入系统用户名密码（md5后密码，验证用）" )
	private String joinUserPassword;

	@TableField(value = "join_outUrl")
  	@ApiModelProperty(value = "接入系统url" )
	private String joinOutUrl;

	@TableField(value = "join_formType")
	@ApiModelProperty(value = "接入系统表单类型(1：通用表单；2：定制表单；3：原生表单)" )
	private Integer joinFormType;

	@TableField(value = "join_protocolMode")
	@ApiModelProperty(value = "接入系统附件模式(1：Http外网；2：FTP协议；3：Http内网)" )
	private Integer joinProtocolMode;

	@TableField(value = "join_modeUrl")
	@ApiModelProperty(value = "接入系统附件url" )
	private String joinModeUrl;

	@TableField(value = "join_bpmUrl")
  	@ApiModelProperty(value = "接入bpm系统url" )
	private String joinBpmUrl;

	@TableField(value = "join_type")
	@ApiModelProperty(value = "接入类型(预留)" )
	private String joinType;

	@TableField(value = "hx_appId")
  	@ApiModelProperty(value = "对应的鸿信应用appid（消息推送用）" )
	private String hxAppId;

	@TableField(value = "hx_pubkey")
	@ApiModelProperty(value = "鸿信公众号ID" )
	private String hxPubkey;

	@TableField(value = "hx_pubsecret")
	@ApiModelProperty(value = "鸿信公众号认证秘钥" )
	private String hxPubsecret;

	@TableField(value = "is_push")
	@ApiModelProperty(value = "是否需要推送鸿信(1表示需要，0表示不需要)" )
	private Integer isPush;

	@TableField(value = "notifyUser")
	@ApiModelProperty(value = "通知用户" )
	private String notifyUser;


}
