package com.radlly.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class PropertyConfiguration {
	@Value("${request.unsafe.startWith}")
	private String startWith;
	
	
	
	  /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    public boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

}
