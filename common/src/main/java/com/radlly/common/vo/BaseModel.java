package com.radlly.common.vo;

import java.util.HashMap;

import com.radlly.pageutil.Page;




public class BaseModel implements java.io.Serializable{
	
	private Long uuid;
	private String uuidString;//uuid字符串
	private Page page = new Page();
	private HashMap<String,Object> map = new HashMap<String,Object>();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}	
	public Long getUuid() {
		return uuid;
	}

	public String getUuidString() {
		return uuidString;
	}

	public void setUuidString(String uuidString) {
		this.uuidString = uuidString;
	}

	public void setUuid(Long uuid) {
		if(uuid != null){
			this.uuidString = String.valueOf(uuid);
		}
		this.uuid = uuid;
	}
	
	public HashMap<String,Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String,Object> map) {
		this.map = map;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
