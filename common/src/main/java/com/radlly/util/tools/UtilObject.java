package com.radlly.util.tools;

import java.io.Serializable;

public class UtilObject implements Serializable{
	private int status=0;
	private String msg="";
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "UtilObject [status=" + status + ", msg=" + msg
				+ ", getStatus()=" + getStatus() + ", getMsg()=" + getMsg()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
