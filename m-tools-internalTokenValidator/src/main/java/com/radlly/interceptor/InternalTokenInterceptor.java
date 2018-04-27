package com.radlly.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.radlly.configuration.PropertyConfiguration;
import com.radlly.constants.CommonConstants;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class InternalTokenInterceptor implements RequestInterceptor{

	@Autowired
	private PropertyConfiguration properties;
	@Override
	public void apply(RequestTemplate template) {
        if(!StringUtils.isBlank(properties.getInternalToken())) {
        	template.header(CommonConstants.TOKEN_INTERNAL, properties.getInternalToken());
        }
	}

}
