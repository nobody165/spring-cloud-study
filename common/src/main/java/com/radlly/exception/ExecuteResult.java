package com.radlly.exception;

import java.util.HashMap;
import java.util.Map;

public class ExecuteResult {

    public static Map<String, Object> jsonReturn(int statusCode) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功");
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败，请重试");
        }
        jsonObj.put("closeCurrent", true);
        return jsonObj;
    }

    public static Map<String, Object> jsonReturn(int statusCode, boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功");
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败，请重试");
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    public static Map<String, Object> jsonReturnJudge(int statusCode, boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功");
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "该类别下有文章存在，不允许删除");
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    public static Map<String, Object> jsonReturn(int statusCode, String msg) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功 " + msg);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败:" + msg);
        }
        jsonObj.put("closeCurrent", true);
        return jsonObj;
    }
    
    public static Map<String,Object> jsonExceptionReturn(Integer errorCode  ,String errorMessage)
    {
    	Map<String, Object> jsonObj = new HashMap<String, Object>();
    	jsonObj.put("errorCode", errorCode);
    	jsonObj.put("errorMessage", errorMessage);
    	return jsonObj;
    }

    /**
     * 当捕捉到 AppException 时候调用这个方法获得返回的 jsonObj , 为 ExceptionResolver 定制
     * @param status
     * @param msg
     * @return
     */
    public static Map<String, Object> jsonReturnCallOnAppException(int status, String msg) {
        return jsonReturn(status, msg);
    }

    public static Map<String, Object> jsonReturn(int statusCode, String msg, boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功 " + msg);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败:" + msg);
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    /**
     * create by LQZ 成功后刷新某一个模块
     * @param statusCode
     * @param msg
     * @param tabid
     * @param closeCurrent
     * @return
     */
    public static Map<String, Object> jsonReturn(int statusCode, String msg, String tabid, boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功 " + msg);
            jsonObj.put("tabid", tabid);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败:" + msg);
            jsonObj.put("tabid", tabid);
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    /**
     * create by LQZ 图片上传返回参数
     * @param statusCode    状态码
     * @param msg           消息
     * @param filename      文件名称
     * @param closeCurrent  是否关闭当前窗口
     * @return
     */
    public static Map<String, Object> jsonReturn(int statusCode, String msg, boolean closeCurrent, String filename, String path) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("filename", filename);
            jsonObj.put("path", path);
            jsonObj.put("message", "操作成功 " + msg);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("filename", "");
            jsonObj.put("path", "");
            jsonObj.put("message", "操作失败:" + msg);
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

}
