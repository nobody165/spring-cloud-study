package com.radlly.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radlly.exception.RadllyException;

@Component
public class JsonHelper {
	private static Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	
	ObjectMapper mapper = new ObjectMapper(); 
//	public static String parsetoString() {
//		ObjectMapper objectMapper = new ObjectMapper();
//	}
	public String beanToJson(Object obj){  
	    // 这里异常都未进行处理，而且流的关闭也不规范。开发中请勿这样写，如果发生异常流关闭不了  
		
	        StringWriter writer = new StringWriter();  
	        JsonGenerator gen;
	        String json = null;
			try {
				gen = new JsonFactory().createJsonGenerator(writer);
				mapper.writeValue(gen, obj);
				gen.close();  
		        json = writer.toString();  
		        writer.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	       
	        return json;  
	    } 
	
	public Map<String,Object> jsonToMap(String json){  
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return map;  
	 } 
	
	public String mapToJson(Map map) {  
	    try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
	 } 
	/**
	 * 
	 * @param json json String
	 * @param key
	 * @return
	 */
	public Object getJsonValue(String json,String key) {
		if(StringUtils.isBlank(json))throw new RadllyException(" param json can not be null!");
		if(StringUtils.isBlank(key))throw new RadllyException(" param key can not be null!");
		Map<String,Object> map = this.jsonToMap(json);
		return map.get(key);
	}
	/**
	 * 
	 * @param json can not be null but ""
	 * @param key can not be null and ""
	 * @param value can not be null and ""
	 */
	public String addJsonValue(String json,String key,Object value) {
		if(null==json)throw new RadllyException(" param key can not be null!");
		if(StringUtils.isBlank(key))throw new RadllyException(" param key can not be null!");
		if(null==value)throw new RadllyException(" param value can not be null!");
		Map<String,Object> map ;
		if(StringUtils.isBlank(json)) {
			map = new HashMap<String,Object>();			
		}else {
			 map = this.jsonToMap(json);
		}	
		map.put(key, value);
		return mapToJson(map);
	}
	
	public String createJsonValue(String key,Object value) {
		if(StringUtils.isBlank(key))throw new RadllyException(" param key can not be null!");
		if(null==value)throw new RadllyException(" param value can not be null!");
		Map<String,Object> map = new HashMap<String,Object>();			
		map.put(key, value);
		return  mapToJson(map);
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		 String json = "{\"id\":1333,\"name\":\"王雪慧\",\"desc\":\"做错事 :0\",\"age\":0}";
		 JsonHelper jsonHelper = new JsonHelper();
		 int j =  (int) jsonHelper.getJsonValue(json, null);
	     logger.debug(String.valueOf(j));
	     
	}
}
