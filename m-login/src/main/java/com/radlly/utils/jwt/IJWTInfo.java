package com.radlly.utils.jwt;

/**
 * Created by ace on 2017/9/10.
 */
public interface IJWTInfo {
	
	public static final String TOKEN="token";
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getId();

}
