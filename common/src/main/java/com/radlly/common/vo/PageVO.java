package com.radlly.common.vo;


/**
 * <p>Description: 分页参数对象 </p>
 * <p>
 * <p>Company: 梯梯达 www.ettda.com</p>
 *
 * @author LiangQuanZhong
 * @date 2017/9/6
 */
public class PageVO {
    private int pageSize = 10;
    private int page = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
    	if(pageSize<1)
    		this.pageSize = 1;
    	else
    	this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
    	if(page<1)
    		this.page=1;
    	else
    		this.page = page;
    }
}
