package com.radlly.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Data;

@Data
public class BaseObject {
	
	private long uuid;
	
	private Object obj;
	
	private String jsonObj;
	
	public String getJsonObj() {
		String obj = this.getObj().toString();		
		if(obj.startsWith("{")) {
			obj = obj.replace("{","");
		}
		if(obj.endsWith("}")) {
			obj = obj.replace("}","");
		}
		String[] arrys = obj.split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(int i =0 ;i<arrys.length;i++) {
			String s = arrys[i].replace("=", ":");
			map.put(s.substring(0, s.indexOf(":")).trim(), s.substring(s.indexOf(":")+1, s.length()).trim());			
		}
		 String str = JSON.toJSONString(map);
		 this.setJsonObj(str);
		return jsonObj;
	}

}
