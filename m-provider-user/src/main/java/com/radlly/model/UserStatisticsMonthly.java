package com.radlly.model;

public class UserStatisticsMonthly {
	
	private String month;
	
	private String userName;
	
	private Long userId;
	
	private Integer woCount;


	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getWoCount() {
		return woCount;
	}

	public void setWoCount(Integer woCount) {
		this.woCount = woCount;
	}
	
	public void addWoCount() {
		this.setWoCount(this.getWoCount()+1);
	}

	@Override
	public String toString() {
		return "UserStatisticsMonthly [month=" + month + ", userName=" + userName + ", userId=" + userId + ", woCount="
				+ woCount + "]";
	}



}
