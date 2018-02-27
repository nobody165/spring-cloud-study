package com.radlly.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radlly.util.json.AppResponse;

public class ExceptionResolver extends AbstractHandlerExceptionResolver {
	Logger log = Logger.getLogger(ExceptionResolver.class);
	
	 public static final String userToken = "tokenStr";//参数请求失败

	public static final String DEFAULT_EXCEPTION_ATTRIBUTE = "exception";

	private String[] pathPatterns;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private Properties exceptionMappings;

	private String defaultErrorView;

	private Integer defaultStatusCode;

	private Map<String, Integer> statusCodes = new HashMap<String, Integer>();

	private String exceptionAttribute = DEFAULT_EXCEPTION_ATTRIBUTE;

	public void setExceptionMappings(Properties mappings) {
		this.exceptionMappings = mappings;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	public void setStatusCodes(Properties statusCodes) {
		for (Enumeration<?> enumeration = statusCodes.propertyNames(); enumeration
				.hasMoreElements();) {
			String viewName = (String) enumeration.nextElement();
			Integer statusCode = new Integer(statusCodes.getProperty(viewName));
			this.statusCodes.put(viewName, statusCode);
		}
	}

	public void setPathPatterns(String[] pathPatterns) {
		this.pathPatterns = pathPatterns;
	}

	public void addStatusCode(String viewName, int statusCode) {
		this.statusCodes.put(viewName, statusCode);
	}

	public Map<String, Integer> getStatusCodesAsMap() {
		return Collections.unmodifiableMap(statusCodes);
	}

	public void setDefaultStatusCode(int defaultStatusCode) {
		this.defaultStatusCode = defaultStatusCode;
	}

	public void setExceptionAttribute(String exceptionAttribute) {
		this.exceptionAttribute = exceptionAttribute;
	}

	public boolean matches(String lookupPath, PathMatcher pathMatcher) {
		if (pathPatterns == null) {
			return true;
		} else {
			for (String pathPattern : pathPatterns) {
				if (pathMatcher.match(pathPattern, lookupPath)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		AppResponse res = new AppResponse();
		String tokenStr = request.getHeader(userToken);
		res.setTokenStr(tokenStr);
		if (ex != null) {
			int status;
			String desc;
			
			if (ex instanceof BusinessException) {
				BusinessException exception = (BusinessException) ex;
				status = exception.getStatus();
				desc = exception.getMsg();
				
				logger.info(String
						.format("AppException Status:%d   Description:%s",
								status, desc));
				res.setCode(status);
				res.setMessage(desc);
//				appJson.setObj(ExecuteResult.jsonExceptionReturn(
//						Integer.valueOf(status), desc));
			} else {
				desc = ExceptionStatus
						.getDesc(ExceptionStatus.SERVER_ERROR);
				res.setCode(ExceptionStatus.SERVER_ERROR);
				res.setMessage(desc);

//				appJson.setObj(ExecuteResult.jsonExceptionReturn(
//						Integer.valueOf(AppExceptionStatus.SERVER_ERROR), desc));

			}
			ObjectMapper mapper = new ObjectMapper();

			try {
				JsonGenerator oJsonGenerator = mapper.getFactory()
						.createGenerator(response.getOutputStream(),
								JsonEncoding.UTF8);
				mapper.writeValue(oJsonGenerator, res);
			} catch (IOException var4) {
				logger.error("Could not write JSON: " + var4.getMessage(), var4);
				var4.printStackTrace();
			}
			this.doCloseOutputStream(response);
		}
		return null;
	
	}

	protected String determineViewName(Exception ex, HttpServletRequest request) {
		String viewName = null;
		// Check for specific exception mappings.
		if (this.exceptionMappings != null) {
			viewName = findMatchingViewName(this.exceptionMappings, ex);
		}
		// Return default error view else, if defined.
		if (viewName == null && this.defaultErrorView != null) {
			if (logger.isDebugEnabled()) {
				logger.info("Resolving to default view '"
						+ this.defaultErrorView + "' for exception of type ["
						+ ex.getClass().getName() + "]");
			}
			viewName = this.defaultErrorView;
		}
		return viewName;
	}

	protected String findMatchingViewName(Properties exceptionMappings,
			Exception ex) {
		String viewName = null;
		String dominantMapping = null;
		int deepest = Integer.MAX_VALUE;
		for (Enumeration<?> names = exceptionMappings.propertyNames(); names
				.hasMoreElements();) {
			String exceptionMapping = (String) names.nextElement();
			int depth = getDepth(exceptionMapping, ex);
			if (depth >= 0 && depth < deepest) {
				deepest = depth;
				dominantMapping = exceptionMapping;
				viewName = exceptionMappings.getProperty(exceptionMapping);
			}
		}
		if (viewName != null && logger.isDebugEnabled()) {
			logger.info("Resolving to view '" + viewName
					+ "' for exception of type [" + ex.getClass().getName()
					+ "], based on exception mapping [" + dominantMapping + "]");
		}
		return viewName;
	}

	protected int getDepth(String exceptionMapping, Exception ex) {
		return getDepth(exceptionMapping, ex.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class<?> exceptionClass,
			int depth) {
		if (exceptionClass.getName().contains(exceptionMapping)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(),
				depth + 1);
	}

	protected Integer determineStatusCode(HttpServletRequest request,
			String viewName) {
		if (this.statusCodes.containsKey(viewName)) {
			return this.statusCodes.get(viewName);
		}
		return this.defaultStatusCode;
	}

	protected void applyStatusCodeIfPossible(HttpServletRequest request,
			HttpServletResponse response, int statusCode) {
		if (!WebUtils.isIncludeRequest(request)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Applying HTTP status code " + statusCode);
			}
			response.setStatus(statusCode);
			request.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE,
					statusCode);
		}
	}

	protected ModelAndView getModelAndView(String viewName, Exception ex,
			HttpServletRequest request) {
		return getModelAndView(viewName, ex);
	}

	protected ModelAndView getModelAndView(String viewName, Exception ex) {
		ModelAndView mv = new ModelAndView(viewName);
		if (this.exceptionAttribute != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Exposing Exception as model attribute '"
						+ this.exceptionAttribute + "'");
			}
			Map model = new HashMap();

			model.put("message", "系统繁忙，请稍后再试。");
			mv.addAllObjects(model);
		}
		return mv;
	}

	private void doCloseOutputStream(HttpServletResponse response) {
		response.setStatus(200);
		try {
			OutputStream out = response.getOutputStream();
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
