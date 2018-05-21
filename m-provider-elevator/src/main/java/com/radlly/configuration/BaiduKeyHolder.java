package com.radlly.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.radlly.model.Location;
import com.radlly.utils.BaiduLocationUtil;

import io.netty.util.internal.StringUtil;
import lombok.Getter;
import lombok.Setter;
@Component
@ConfigurationProperties(prefix="baidu")
public class BaiduKeyHolder {
	private static final String defaultAddr = "成都市";
	private static Logger logger = LoggerFactory.getLogger(BaiduKeyHolder.class);
	
	@Getter @Setter private List<String> keys ;  
	
	private String availableKey;

	public String refreshAvailableKey() {
		BaiduLocationUtil util = new BaiduLocationUtil();		
		for(String key:keys){
			Location l = util.getLngAndLat(defaultAddr, key);
			if(l.getCode()==Location.CODE_SUCCESS) {
				this.setAvailableKey(key);
				logger.debug("using key:"+key);
				break;
			}else {
				logger.debug("vaild key:"+key+" msg:"+l.getMsg());
			}
		}
		return this.getAvailableKey();
	}

	public String getAvailableKey() {
		if(StringUtil.isNullOrEmpty(availableKey))refreshAvailableKey();
		return availableKey;
	}

	public void setAvailableKey(String availableKey) {
		this.availableKey = availableKey;
	}
	
	

	
	
	
	
	
}
