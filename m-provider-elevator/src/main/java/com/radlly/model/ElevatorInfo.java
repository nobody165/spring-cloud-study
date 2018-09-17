package com.radlly.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
 * @author radlly.xu
 * default page attribute are start:0 and size:10
 *
 */

@Data
public class ElevatorInfo extends BaseObject{
	
	public final static int DEL_FALSE = 0;
	public final static int DEL_TRUE = 1;
	/**
	 * necessary attrs
	 */
	@ApiModelProperty(value="电梯地址",name="buildAddress",required=true)
	private String buildAddress;// 所在地址
	@ApiModelProperty(value="物业公司名称",name="propertyCom",required=false)
	private String propertyCom;
	@ApiModelProperty(value="纬度",name="latitude",required=false)
	private Double latitude;
	@ApiModelProperty(value="经度",name="longitude",required=false)
	private Double longitude;
	@ApiModelProperty(value="电梯号",name="evCode",required=false)
	private String evCode;//电梯号
	@ApiModelProperty(value="注册代码",name="regCode",required=false)
	private String regCode;// 注册代码
	@ApiModelProperty(value="梯号",name="evOrder",required=false)
	private String evOrder;//梯号
	@ApiModelProperty(value="品牌名称",name="brand",required=false)
	private String brand;// 品牌
	@ApiModelProperty(value="类型(eg: 直梯，扶梯，液压梯)",name="evType",required=false)
	private String evType;// 类型(直梯，扶梯，液压梯)
	@ApiModelProperty(hidden=true)
	private Integer del = DEL_FALSE;
	@ApiModelProperty(hidden=true)
	private Date createAt;
	
	private Object objArrs;
	
	private String obj;
	
public	enum  Evtype{
		VERTICAL("直梯"),UNDIRECT("杂物梯"),HYDRAULIC("液压梯"),ESCALATOR("扶梯");  
	    // 成员变量  
	    private String name;  
	    // 构造方法  
	    private Evtype(String name) {  
	        this.name = name;  
	    }  
	    
	    // get set 方法  
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    
	}
}



