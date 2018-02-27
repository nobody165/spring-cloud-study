package com.radlly.common.web;



public class BaseWebModel {
	//查询条件字符串
	private String queryJsonStr = "";
	//当前页
	private int nowPage = 1;
	//每页显示条数
	private int pageShow = 0;
	//每页显示条数
	private int limit = 10;
	//从第几条开始
	private int offset = 0;
	public String getQueryJsonStr() {
		return queryJsonStr;
	}
	public void setQueryJsonStr(String queryJsonStr) {
		this.queryJsonStr = queryJsonStr;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getPageShow() {
		return pageShow;
	}
	public void setPageShow(int pageShow) {
		this.pageShow = pageShow;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		return "BaseWebModel [queryJsonStr=" + queryJsonStr + ", nowPage="
				+ nowPage + ", pageShow=" + pageShow + "]";
	}
}
