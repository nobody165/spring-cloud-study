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
import com.radlly.feign.TokenFeignClient;

@Component
@Order(-2)
@WebFilter(filterName = "internalTokenFilter", urlPatterns = "/*")
public class InternalTokenFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(InternalTokenFilter.class);

	@Autowired
	private TokenFeignClient tokenFeignClient;
	@Autowired
	private PropertyConfiguration propertyConfiguration;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		final String requestUri = r.getRequestURI();
		if (propertyConfiguration.isStartWith(requestUri)) {
			logger.debug("request start with :" + requestUri + " , skip internal filter!");
			chain.doFilter(request, response);
		} else {
			String internalValue = r.getHeader(tokenFeignClient.getInternalTokenName());
			if (null == internalValue || !tokenFeignClient.getAllInternalToken().contains(internalValue)) {
				response.getWriter().write("request not promised !");
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
