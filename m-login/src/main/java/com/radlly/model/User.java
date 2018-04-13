package com.radlly.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    /**
     * 生成的serialVersionUID
     */
    private static final long serialVersionUID = 5231134212346077681L;

    private Long uuid;

    private String username;

    private String phoneNumber;

    private String password;

    private Date regDate;

    private Date lastGetPsdTime;

    private Byte status;


	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
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