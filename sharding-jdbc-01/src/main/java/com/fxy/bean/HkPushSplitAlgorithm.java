package com.fxy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 移动审批表拆分算法表
 *
 * @author fengxiaoyang
 * @date 2021-7-4 9:44:23
 */
@Data
@Accessors(chain = true)
@ApiModel("移动审批表拆分算法表")
@TableName("hk_push_split_algorithm")
public class HkPushSplitAlgorithm implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

  	@ApiModelProperty(value = "业务消息id" )
	private String appRequestId;

  	@ApiModelProperty(value = "当前操作用户ID" )
	private String userId;

  	@ApiModelProperty(value = "是否发起人true是，false否" )
	private String createTime;

  	@ApiModelProperty(value = "null" )
	private Date updateTime;


}
