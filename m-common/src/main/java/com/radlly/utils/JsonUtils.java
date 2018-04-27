package com.radlly.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.radlly.model.AppObj;
import com.radlly.model.TestUser;

public class JsonUtils {
	/***
	 * 解析为字符串
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static String fromString(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getString(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为布尔
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static Boolean formBoolean(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getBoolean(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	/***
	 * 解析为String
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static String formString(String jsonString, String key, String skey) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            JSONObject jsonObject1 = jsonObject.getJSONObject(key);
	            String jsonObject2 = jsonObject1.getString(skey);
	            return jsonObject2;
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为Int
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static int formInt(String jsonString, String key, String skey) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            JSONObject jsonObject1 = jsonObject.getJSONObject(key);
	            int jsonObject2 = jsonObject1.getInteger(skey);
	            return jsonObject2;
	        } else {
	            return -1;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    }
	}

	/***
	 * 解析为数字
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static Integer formInteger(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getInteger(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为长位十进制数
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static BigDecimal formBigDecimal(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getBigDecimal(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为双精度
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static Double formDouble(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getDouble(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为浮点数
	 *
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static Float formFloat(String jsonString, String key) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(jsonString);
	            return jsonObject.getFloat(key);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 解析为对象
	 *
	 * @param jsonString
	 * @param key
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T fromBean(String jsonString, String key, Class<T> t) {
	    if (jsonString != null) {
	        try {
	            JSONObject jsonObj = JSONObject.parseObject(jsonString);
	            return JSONObject.toJavaObject(jsonObj.getJSONObject(key), t);
	        } catch (Exception e) {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}

	/***
	 * 解析为列表
	 *
	 * @param jsonString
	 * @param key
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> ArrayList<T> fromList(String jsonString, String key, Class<T> t) {
	    ArrayList<T> list = new ArrayList<T>();
	    if (jsonString != null && jsonString.length() > 0) {
	        try {
	            JSONObject jsonObj = JSONObject.parseObject(jsonString);
	            JSONArray inforArray = jsonObj.getJSONArray(key);
	            for (int index = 0; index < inforArray.size(); index++) {
	                list.add(JSONObject.toJavaObject(
	                        inforArray.getJSONObject(index), t));
	            }
	        } catch (Exception e) {
	        }
	    }
	    return list;
	}

	/***
	 * 直接解析为bean
	 *
	 * @param jsonString
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T fromBean(String jsonString, Class<T> t) {
	    try {
	        if (jsonString != null && jsonString.length() > 0) {
	            return JSON.parseObject(jsonString, t);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/***
	 * 直接解析为list
	 *
	 * @param jsonString
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> ArrayList<T> fromList(String jsonString, Class<T> t) {
	    ArrayList<T> list = null;
	    try {
	        list = new ArrayList<T>();
	        if (jsonString != null && jsonString.length() > 0) {
	            list.addAll(JSON.parseArray(jsonString, t));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	/***
	 * 将列表转换为json
	 *
	 * @param listBean
	 * @return
	 */
	public static <T> String ArrayToJson(ArrayList<T> listBean) {
	    String jsonString = JSON.toJSONString(listBean);
	    return jsonString;
	}

	/***
	 * 将类转为json
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> String BeanToJson(Object obj) {
	    String jsonsString = JSON.toJSONString(obj);
	    return jsonsString;
	}

	public static void main(String[] args) {

		AppObj app = new AppObj("logout successful");
		TestUser a = new TestUser();
		a.setPassword("1");
		a.setUsername("2");
		app.setObj(JSONObject.toJSONString(a));
		System.out.print(JsonUtils.fromBean(app.getObj().toString(), TestUser.class));
	}
}
