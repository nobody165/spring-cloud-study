package com.radlly.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by sun on 2017-3-21.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(Exception e) throws Exception {
    	logger.error(e.getMessage(), e);
        return e.getMessage();
    }
    
//    @ExceptionHandler(BusinessException.class)
//    @ResponseBody
//    AppResponse handleBusinessException(BusinessException e){
//    	logger.error(e.getMessage(), e);
//
//        AppResponse response = new AppResponse();
//        response.setMessage(e.getMessage());
//        return response;
//    }
}
