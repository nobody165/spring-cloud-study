package com.radlly.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.radlly.configuration.TokenConfiguration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//@Configuration
public class FeignInterceptor implements RequestInterceptor{
	@Autowired
	 private TokenConfiguration tokenConfiguration;

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String internalValue= request.getHeader(tokenConfiguration.getInternalName());
        if(!StringUtils.isBlank(internalValue)) {
        	template.header(tokenConfiguration.getInternalName(), internalValue);
        }
	}

}
