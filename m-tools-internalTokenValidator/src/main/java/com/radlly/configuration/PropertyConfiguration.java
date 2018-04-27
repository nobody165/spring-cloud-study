package com.radlly.configuration;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class PropertyConfiguration {
	@Value("${request.ignore.startWith}")
	private String startWith;
	
	private String internalToken;
	
	
	
	  /**
     * URI鏄惁浠ヤ粈涔堟墦澶�
     *
     * @param requestUri
     * @return
     */
    public boolean isStartWith(String requestUri) {
        boolean flag = false;
        if(!StringUtils.isEmpty(startWith)) {
        	for (String s : startWith.split(",")) {
                if (requestUri.startsWith(s)) {
                    return true;
                }
            }
        }        
        return flag;
    }

}
