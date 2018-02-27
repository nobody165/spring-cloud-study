package com.radlly.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class StringHelper {

	private final static String SPECIAL_WORD = "[~`!！@·$%^&_*+=|%{}【】：:\"\'\\;；\\\\<>《》，,.。？?/]+";// 不能包含的特殊字
	private final static String SPECIAL_EMP_CHAR = "^[-()（）\\[\\]#a-zA-Z0-9\\u4e00-\\u9fa5]*";
	private final static String SPECIAL_LEGAL_PERSON = "^[a-zA-Z0-9\\u4e00-\\u9fa5]*";
	private final static String PHONE = "^[1][34578]\\d{9}$";// 电话号码校验
	private final static String NUMBER_PATTERN = "[0-9]*";// 校验是否是数字
	private final static String ENGLISH_PATTERN = "[a-zA-Z0-9]*";// 校验是否是数字、字母
	private final static long LONG_MAX = 9223372036854775807l;// 校验是否是数字
	
	/**
     * 检查请求输入参数是否为空
     * @param objects
     *  
     */
    public static boolean isEmpty(Object... objects)  {
        for (Object object : objects) {
            if (StringUtils.isEmpty(object)) {
            	return false;
            } else {
                if (object instanceof String) {
                    if (StringUtils.isEmpty(((String) object).trim())) {
                    	return false;
                    }
                } else
                if (object instanceof List) {
                    if (CollectionUtils.isEmpty((List) object)) {
                    	return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 检查请求输入参数长度
     * @param param     参数与
     * @param length    长度
     * @return default:-1  param==length 0,param>length:1,param<length:-1
     */
    public static int checkParametersLength(String param, int length) {
    	int result = -1;
        if(isEmpty(param) ){
        	if(param.length() > length)result = 1;
        	else if(param.length() == length)result = 0;
        	else if(param.length() < length)result = -1;           
        }
        return result;
    }

	/**
	 * 判断是不是英文
	 * 
	 * @param param
	 * @return
	 */
	public static boolean checkNumberOrEnglish(String... param) {		
		for (String keyword : param) {
			if (!isEmpty(keyword)) {
				Matcher matcher = Pattern.compile(ENGLISH_PATTERN).matcher(keyword);
				if (!matcher.matches()) {
					return false;
				}
			}
		}
		return true;
	}
	
	 /**
     * 检查特殊字符
     * @param keywords
     * @return
     */
    public static boolean isSpecialWord(String... keywords) {
        for(String keyword : keywords) {
            if (!isEmpty(keyword)) {
                Matcher matcher = Pattern.compile(SPECIAL_WORD).matcher(keyword);
                if (matcher.find()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 检查特殊字符
     * @param keywords
     * @return
     */
    public static boolean checkSpecialWordExist(String... keywords) {
        for(String keyword : keywords) {
            if (!isEmpty(keyword)) {
                Matcher matcher = Pattern.compile(SPECIAL_WORD).matcher(keyword);
                if (matcher.find()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查特殊字符
     * @return
     */
    public static boolean checkSpecialWordEmpNo(String... keywords) {
        for(String keyword : keywords) {
            if (!isEmpty(keyword)) {
                Matcher matcher = Pattern.compile(SPECIAL_EMP_CHAR).matcher(keyword);
                if (matcher.find()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查特殊字符
     * @return
     */
    public static boolean checkSpecialWordLegalPerson(String keyword) {
        if (!isEmpty(keyword)) {
            Matcher matcher = Pattern.compile(SPECIAL_LEGAL_PERSON).matcher(keyword);
            if (matcher.find()) {
            	return true;
            }
        }
        return false;
    }
    
    /**
     * 校验手机格式是否正确
     * @param phone
     */
    public static boolean isPhone(String phone){
        if(!isEmpty(phone)){
            if(phone.matches(PHONE)) {
               return true;
            }
        }
        return false;
    }
    
    /**
     * 验证身份证
     * @param iDCard 身份证
     *
     * @return
     */
    public static boolean isIDCard(String iDCard){    	
        if(!isEmpty(iDCard)){
            String regexIDCard15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            String regexIDCard18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|x|X){1}$";
            if(iDCard.length() != 15 && iDCard.length() != 18){
            	 return false;
            }
            if(iDCard.length() == 15 && !iDCard.matches(regexIDCard15)) {
            	 return false;
            } else if(iDCard.length() == 18 && !iDCard.matches(regexIDCard18)) {
            	 return false;
            }
            else return true;
        }
        return false;
    }
    /**
     * 校验是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(!isEmpty(str.trim())) {
            Matcher matcher = Pattern.compile(NUMBER_PATTERN).matcher(str);
            if (!matcher.matches()) {//不是数字
               return false;
            }
        }
        return true;
    }
    

}
