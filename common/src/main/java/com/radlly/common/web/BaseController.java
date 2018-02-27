package com.radlly.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.radlly.common.vo.PageVO;
import com.radlly.pageutil.Page;
import com.radlly.util.Constants.SystemConstant;
import com.radlly.util.json.AppResponse;
import com.radlly.util.restfull.RestClientUtil;
import com.radlly.util.token.TokenUtil;

/**
 * <p>Description:  公用controller， 每个controller 必须继承此 controller</p>
 * <p>
 * <p>Company: 梯梯达 www.ettda.com</p>
 *
 * @author LiangQuanZhong
 * @date 2017/9/4
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    /**
     * 获取token
     * @return
     */
    protected String getToken(){
    	String tk = null ;
    	String internalFlag = request.getHeader(RestClientUtil.INTERNAL_FLAG_KEY);
    	if(null!=internalFlag){
    		int i = Integer.valueOf(internalFlag);
    		if(i==RestClientUtil.INTERNAL_FLAG_VALUE)
    			tk = TokenUtil.INTERNAL_TOKEN;
    	}else{
    		tk = request.getHeader(SystemConstant.userToken);
    	}    	
        return tk;
    }
    
    protected String getHeaderValue(String key){
        return request.getHeader(key);
    }
    
    protected AppResponse getTokenAppJson(){
    	AppResponse appjson = new AppResponse();
    	appjson.setTokenStr(getToken());
    	return appjson;
    }
    
    /**
     * 分页
     * @param pageVO
     * @return
     */
    protected Page buildPage(PageVO pageVO){
        Page page = new Page();
        page.setNowPage(pageVO.getPage());
        page.setPageShow(pageVO.getPageSize());
        return page;
    }

}
