package com.radlly.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.radlly.feign.LoginFeignClient;
import com.radlly.model.AppObj;
import com.radlly.response.InternalObj;
import com.radlly.response.TokenErrorResponse;
import com.radlly.utils.JsonUtils;


/**
 * ${DESCRIPTION}
 *
 * @author heng.xu
 * @create 2018-03-23 8:25
 */
@Component
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    @Value("${gate.ignore.startWith}")
    private String startWith;


    @Autowired
	private LoginFeignClient loginFeignClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        boolean returnAble = false;
        String token = "";
        
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
        	returnAble = true;        	
        	logger.debug("request start with :" + requestUri+" , skip access internal filter!");
        }    	
        if(!returnAble){
        	  token = request.getHeader("token");//测试暂时写死,以后加入yml
              if(token==null) {
              	  setFailedRequest(JSON.toJSONString(new TokenErrorResponse("token is not exist!")), 200);
              	  ctx.setSendZuulResponse(false);  
	              ctx.setResponseStatusCode(401);  
	              ctx.setResponseBody("{\"result\":\"token validate faild!\"}");  
	              ctx.getResponse().setContentType("text/html;charset=UTF-8");
	              return null;
              }       	
        }
        AppObj appObj = loginFeignClient.validate(token);
        if(appObj.getCode()==AppObj.SSUCCESS) {        	
        	InternalObj internalObj = JsonUtils.fromBean(JsonUtils.BeanToJson(appObj.getObj()), InternalObj.class);
        	 ctx.addZuulRequestHeader(internalObj.getInternalTokenName(), internalObj.getInternalToken());
        }       
        return null;
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
    	logger.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

}
