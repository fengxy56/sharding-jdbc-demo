package com.fxy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2021-7-4 20:13:51
 */
@Data
@Accessors(chain = true)
@ApiModel("null")
@TableName("t_order")
public class TOrder implements Serializable {

  	@ApiModelProperty(value = "null" )
	private Integer id;

  	@ApiModelProperty(value = "null" )
	private String name;


}
