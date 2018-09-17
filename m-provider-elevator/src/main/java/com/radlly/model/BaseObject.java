package com.radlly.model;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Component
@Data
public class BaseObject {
	@ApiModelProperty(hidden=true)
	private long uuid;	
	
	private int pageStart = 0;
	
	private int pageEnd = 10;	

}
