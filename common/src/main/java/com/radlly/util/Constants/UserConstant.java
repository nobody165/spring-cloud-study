package com.radlly.util.Constants;

/**
 * <p>Description:  管理员公用资源</p>
 * <p>
 * <p>Company: 中景恒基 www.mwteck.com</p>
 *
 * @author LiangQuanZhong
 * @date 2017/6/13
 */
public class UserConstant {
    //请求成功或失败
    public static final int success = 200;//参数请求正常
    public static final int failed = 300;//参数请求失败

    //session 名称
    public static final String resourcesObjectSet = "resourcesObjectSet";//
    public static final String resourcesIdSet = "resourcesIdSet";//
    public static final String roleId = "roleId";//用户角色ID
    public static final String portType = "portType";//1：维保；2：物业
    public static final String userInfo = "userInfo";//用户信息
    public static final String username = "username";//用户名称
    public static final String isAdmin = "isAdmin";//角色对象
    public static final String companyId = "companyId";//用户公司ID
}
