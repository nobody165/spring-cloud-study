package com.radlly.mysql;

import com.radlly.util.StringHelper;

public class CharHelper {
	 /**
     * 字符串模糊查询替换_
     * mysql中 _ 表示查询任何字符需要转义
     * @param param
     * @return
     */
    public static String replaceChar(String param){
        if(StringHelper.isEmpty(param)){
            return param;
        }
        return param.replace("_", "\\_");
    }
}
