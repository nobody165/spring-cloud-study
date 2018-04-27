package com.radlly.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.radlly.constants.CommonConstants;
import com.radlly.feign.TokenFeignClient;
import com.radlly.model.AppObj;


/**
 * ${DESCRIPTION}
 *
 * @author heng.xu
 * @create 2018-03-23 8:25
 */
@Component
public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    @Value("${request.ignore.startWith}")
    private String startWith;


    @Autowired
	private TokenFeignClient tokenFeignClient;

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
        AppObj appObj = new AppObj(AppObj.SSUCCESS);
        String internalToken;
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
        	returnAble = true;        	
        	logger.debug("request start with :" + requestUri+" , skip access internal filter!");
        }    	
        if(!returnAble){
        	  token = request.getHeader("token");//测试暂时写死,以后加入yml
              if(token==null) {
//              	  setFailedRequest(JSON.toJSONString(new TokenErrorResponse("token is not exist!")), 200);
              	  ctx.setSendZuulResponse(false);  
	              ctx.setResponseStatusCode(401);  
	              ctx.setResponseBody("{\"result\":\"token validate faild!\"}");  
	              ctx.getResponse().setContentType("text/html;charset=UTF-8");
	              return null;
              }  
              appObj = tokenFeignClient.validate(token);
              if(appObj.getCode()==AppObj.FAIL) {
              	  ctx.setSendZuulResponse(false);  
	              ctx.setResponseStatusCode(401);  
	              ctx.setResponseBody(appObj.toString());  
	              ctx.getResponse().setContentType("text/html;charset=UTF-8");
	              return null;
              }
        }
      
        if(returnAble||appObj.getCode()==AppObj.SSUCCESS) {   
        	if(null!=appObj.getObj()) {
        		internalToken = appObj.getObj().toString();
        	}else {
        		internalToken = tokenFeignClient.getInternalToken();
        	}
        	 ctx.addZuulRequestHeader(CommonConstants.TOKEN_INTERNAL, internalToken);
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


}
