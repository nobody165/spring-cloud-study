package com.radlly.util.restfull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.radlly.exception.BusinessException;

public class RestClientUtil {
	public static String INTERNAL_FLAG_KEY="internalFlag";
	public static int INTERNAL_FLAG_VALUE=1;
	
	private StringBuffer requestAddress ;
	private Map<String,Object> parms =new HashMap<String, Object>();	
	

	public RestClientUtil(StringBuffer requestAddress) {
		super();
		this.requestAddress = requestAddress;
	}

	private void appendParm(StringBuffer path,String key,Object value){
		
		if(!path.toString().contains("?")){
			path.append("?");
		}else if(!path.toString().endsWith("?")){
			path.append("&");
		}
		path.append(key+"="+value);
			
	}
	
	public void addParm(String key,Object value){		
		parms.put(key, value);			
	}
	
	public Map<String, Object> getParms() {
		return parms;
	}
	/**
	 * GET 方式
	 * 参数将拼接到URL后面
	 * @return
	 */
	public String builGetdUrl(){
		if(requestAddress.length()==0)throw new BusinessException("请求地址错误");
		for (Map.Entry<String, Object> entry : parms.entrySet()) {  
			this.appendParm(requestAddress, entry.getKey(), entry.getValue());		  
		}  
		return requestAddress.toString();
			
	}
	
	public void setRequestAddress(String address){
		this.requestAddress = new StringBuffer(address);
	}	

	public StringBuffer getRequestAddress() {
		return requestAddress;
	}

	 /**
     * 设置内部request标识符-POST
     * 并添加参数
     * @return
     */ 
	public HttpEntity<JSONObject> postEntity(){
    	HttpHeaders headers = new HttpHeaders(); 
		headers.set(String.valueOf(INTERNAL_FLAG_KEY), String.valueOf(INTERNAL_FLAG_VALUE));
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObj = new JSONObject();
		for (Map.Entry<String, Object> entry : getParms().entrySet()) {  
			jsonObj.put(entry.getKey(), entry.getValue());		  
		}  
		HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObj,headers);
		return entity;
    }

    /**
     * 设置内部request标识符-GET
     * @return
     */    
	public HttpEntity<JSONObject> getEntity(){
    	HttpHeaders headers = new HttpHeaders(); 
		headers.set(String.valueOf(INTERNAL_FLAG_KEY), String.valueOf(INTERNAL_FLAG_VALUE));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(null,headers);
		return entity;
    }
    	

}
