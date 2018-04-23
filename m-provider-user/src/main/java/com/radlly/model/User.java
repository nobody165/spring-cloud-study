package com.radlly.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class User implements Serializable{
    /**
     * 生成的serialVersionUID
     */
    private static final long serialVersionUID = 5231134212346077681L;

    private Integer uuid;

    @ApiModelProperty(value="用户名")  
    private String username;

    @ApiModelProperty(value="电话号码")  
    private String phoneNumber;

    @ApiModelProperty(value="密码")  
    private String password;

    @ApiModelProperty(value="注册时间")  
    private Date regDate;

    @ApiModelProperty(value="最后登录时间")  
    private Date lastGetPsdTime;

    @ApiModelProperty(value="用户状态")  
    private Byte status;


	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastGetPsdTime() {
		return lastGetPsdTime;
	}

	public void setLastGetPsdTime(Date lastGetPsdTime) {
		this.lastGetPsdTime = lastGetPsdTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}