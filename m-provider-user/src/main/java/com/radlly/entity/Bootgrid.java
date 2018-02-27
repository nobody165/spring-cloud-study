package com.radlly.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Bootgrid表格传值
 */
public class Bootgrid implements Serializable {

	private static final long serialVersionUID = -6410710264392294258L;
	private int current;
	private int rowCount;
	private int maxResults;
	private int firstResult;
	private long total;
	private Object rows;
	private String searchPhrase;
	
	
	
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	private Integer param;
	private Map<String, Object> sort = new HashMap<>();
	
	private String sortType;
	private String sortBy;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = new StringBuffer("%")
				.append(searchPhrase == null ? "" : searchPhrase).append("%")
				.toString();
	}

	public int getFirstResult() {
		return (this.current - 1) * this.rowCount;
	}

	public int getMaxResults() {
		return this.rowCount == -1 ? Integer.MAX_VALUE : this.rowCount;
	}

	public Integer getParam() {
		return param;
	}

	public void setParam(Integer param) {
		this.param = param;
	}

	public Map<String, Object> getSort() {
		if(!sort.isEmpty()){
			Iterator<Entry<String, Object>> it =sort.entrySet().iterator();
			//如果排序字段有多个 则随机取一个进行排序
			Entry<String, Object> e = it.next();
			this.sortType=(String) e.getValue();
			this.sortBy=e.getKey();
		}
		return sort;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSort(Map<String, Object> sort) {
		this.sort = sort;
	}

	public void fieldSort(){
		getSort();
	}
}
