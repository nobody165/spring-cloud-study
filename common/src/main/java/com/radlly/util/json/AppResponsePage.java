package com.radlly.util.json;

public class AppResponsePage extends AppResponse {
	//每页显示几条
		private int pageShow = 10; 
		//总页数
		private int totalPage;
		//总条数
		private int totalCount;
		//本次分页从第几条开始1
		private int start;
		//当前页数
		private int nowPage;
		public int getPageShow() {
			return pageShow;
		}
		public void setPageShow(int pageShow) {
			this.pageShow = pageShow;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getNowPage() {
			return nowPage;
		}
		public void setNowPage(int nowPage) {
			this.nowPage = nowPage;
		}
		@Override
		public String toString() {
			return "AppJsonPage [pageShow=" + pageShow + ", totalPage="
					+ totalPage + ", totalCount=" + totalCount + ", start="
					+ start + ", nowPage=" + nowPage + ", getPageShow()="
					+ getPageShow() + ", getTotalPage()=" + getTotalPage()
					+ ", getTotalCount()=" + getTotalCount() + ", getStart()="
					+ getStart() + ", getNowPage()=" + getNowPage()
					+ ", getCode()=" + getCode() + ", getMessage()="
					+ getMessage() + ", getObj()=" + getObj() + ", toString()="
					+ super.toString() + ", getClass()=" + getClass()
					+ ", hashCode()=" + hashCode() + "]";
		}
		
		
}
