package com.radlly.model;

import java.util.Date;

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
	private String buildAddress;// 所在地址
	private String propertyCom;
	private Double latitude;
	private Double longitude;
	private String evCode;//电梯号
	private String regCode;// 注册代码
	private String evOrder;//梯号
	private String brand;// 品牌
	private String evType;// 类型(直梯，扶梯，液压梯)
	private Integer del = DEL_FALSE;
	private Date createAt;
	
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



