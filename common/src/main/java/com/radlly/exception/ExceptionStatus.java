package com.radlly.exception;


import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: Status.java</p>
 * <p/>
 * <p>Description: 系统平台消息状态码定义</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2014 </p>
 * <p/>
 * <p>Company: 华炜云商科技有限公司 www.hwtech.cc</p>
 * <p/>
 * 系统类异常：1，业务类异常：2
 * 系统设置模块：      11
 * 登录                12
 * 注册                13
 *
 * 用户                21
 * 用户                31
 * 举例：
 * 异常描述：商品分类不存在，code：21301
 *
 * @author LiangQuanZhong
 * @version 1.0
 * @date 2016/12/8
 */
public class ExceptionStatus {
	
	/*
	 * 系统级别 1000-2000
	 */
    @Description(value = "会话过期", key = -1)
    public static int REDIRECT = -1;
    @Description(value = "操作成功", key = 1)
    public static int OK = 1;
    @Description(value = "系统错误", key = 2)
    public static int SERVER_ERROR = 2;
    @Description(value = "无效的请求参数", key = 3)
    public static int INVALID_REQUEST_PARAMETER = 3;
    @Description(value = "操作失败", key = 4)
    public static int INVALID_OPERATION = 4;
    @Description(value = "数据异常", key = 5)
    public static int DATA_ERROR = 5;
    @Description(value = "管理员登录", key = 6)
    public static int ADMIN_LOGIN = 6;
    @Description(value = "网络连接失败", key = 7)
    public static int NET_CONNECT_FAILED = 7;
    @Description(value = "请求地址错误", key = 8)
    public static int NET_CONNECT_ADDRESS_NOTFOUND = 8;    
    @Description(value = "对不起，您暂无操作此功能的权限", key = 9)
    public static int AUTHOR_ERROR = 9;

    @Description(value = "日期格式不正确", key = 1001)
    public static int DATE_ERROR = 1001;
    @Description(value = "不能包含特殊字符", key = 1002)
    public static int PARAM_SPECIAL = 1002;
    @Description(value = "ID非数字错误", key = 1003)
    public static int ID_NUMBER_ERROR = 1003;
    @Description(value = "ID长度错误", key = 1004)
    public static int ID_LENGTH_ERROR = 1004;
    @Description(value = "结束时间必须晚于开始时间", key = 1005)
    public static int START_END_DATE_ERROR = 1005;
    @Description(value = "权限不足，操作失败", key = 1006)
    public static int DEAL_ERROR = 1006;
    @Description(value = "找不到对应的数据", key = 1007)
    public static int DATA_IS_NULL = 1007;
    
    /*
     * 用户 code范围3000-6000
     */
    @Description(value = "用户名和密码不能为空", key = 3000)
    public static int USERNAME_PASSWORD_IS_NULL = 3000;
    @Description(value = "用户不存在", key = 3001)
    public static int USER_NOT_EXIST = 3001;
    @Description(value = "用户已被禁用", key = 3002)
    public static int USER_DISABLE_UNUSEABLE = 3002;
    @Description(value = "用户已被锁定", key = 3003)
    public static int USER_ISLOCK_LOCK = 3003;
    @Description(value = "用户已被删除", key = 3004)
    public static int USER_DEL_YES = 3004;
    @Description(value = "用户名或密码错误", key = 3005)
    public static int USERNAME_PASSWORD_IS_ERROR = 3005;
    @Description(value = "请输入手机号", key = 3006)
    public static int USER_PHONE_NUMBER_IS_NULL = 3006;
    @Description(value = "手机号码格式不正确", key = 3007)
    public static int USER_PHONE_NUMBER_IS_NOT_FORMATTED_CORRECT = 3007;
    @Description(value = "登录过期", key = 3008)
    public static int USER_TOKEN_IS_NULL = 3008;
    @Description(value = "用户数据异常,请联系管理员", key = 3009)
    public static int USER_DATA_EXCEPTOPN = 3009;
   

    protected static Map<Integer, String> descriptionMap = new LinkedHashMap<Integer, String>();


    static {
        Field[] fields = ExceptionStatus.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Description.class)) {
                Description description = field.getAnnotation(Description.class);
                descriptionMap.put(Integer.valueOf(description.key()), description.value());
            }
        }
    }

    public static String getDesc(int status) {
        return descriptionMap.get(status);
    }

    public static void main(String[] args) {
        try {
            PrintStream stream = new PrintStream("STATUS.txt");
            Iterator<Integer> it = descriptionMap.keySet().iterator();
            while (it.hasNext()) {
                Integer key = it.next();
                stream.println(String.format("Status:%d   Description:%s", key, descriptionMap.get(key)));
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
