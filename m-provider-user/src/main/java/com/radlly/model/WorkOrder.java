package com.radlly.model;

import java.io.Serializable;
import java.util.Date;

public class WorkOrder implements Serializable {
	/**
	 * 生成的serialVersionUID
	 */
	private static final long serialVersionUID = 5231134212346077681L;

	private Long uuid;

	private String code;

	private Long userId;

	private Integer type;

	private Date date;

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}