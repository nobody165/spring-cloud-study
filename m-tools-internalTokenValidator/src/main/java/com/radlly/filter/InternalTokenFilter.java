package com.radlly.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.radlly.configuration.PropertyConfiguration;
import com.radlly.constants.CommonConstants;
import com.radlly.feign.InternalTokenFeignClient;

@Component
@Order(-2)
@WebFilter(filterName = "internalTokenFilter", urlPatterns = "/*")
public class InternalTokenFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(InternalTokenFilter.class);

	@Autowired
	private InternalTokenFeignClient internalTokenFeignClient;
	
	@Autowired
	private PropertyConfiguration propertyConfiguration;


	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		final String requestUri = r.getRequestURI();
		if (propertyConfiguration.isStartWith(requestUri)) {
			logger.debug("request start with :" + requestUri + " , skip internal filter!");
			chain.doFilter(request, response);
			propertyConfiguration.setInternalToken(internalTokenFeignClient.getInternalToken());
		} else {
			String internalValue = r.getHeader(CommonConstants.TOKEN_INTERNAL);			
			if (null != internalValue ) {
				List<String> internalTokens = internalTokenFeignClient.getAllInternalToken();
				if(internalTokens.contains(internalValue)) {
					propertyConfiguration.setInternalToken(internalValue);
					chain.doFilter(request, response);
				}else {
					response.getWriter().write("token expired !");
				}
			} else {
				response.getWriter().write("request not promised !");
				
			}
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
