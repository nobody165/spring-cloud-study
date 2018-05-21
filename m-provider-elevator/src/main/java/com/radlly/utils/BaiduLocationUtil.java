package com.radlly.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.radlly.configuration.BaiduKeyHolder;
import com.radlly.model.ElevatorInfo;
import com.radlly.model.Location;

/**
 * 
 * @author radlly.xu
 *
 */
@Component
public class BaiduLocationUtil {
	private static Logger logger = LoggerFactory.getLogger(BaiduLocationUtil.class);
	public static final String ERROR_1 = "地址未找到！";
	public static final String ERROR_4 = "服务当日调用次数已超限";
	public static final String ERROR_101 = "服务禁用";
	public static final String ERROR_DEFAULT = "未知错误，请查看系统日志";

	@Autowired
	private BaiduKeyHolder keyholder;
	
	public Location getLngAndLat(String address) {
//		return this.getLngAndLat(address, keyholder.getAvailableKey());
		return this.getLngAndLat(address, "42b8ececa9cd6fe72ae4cddd77c0da5d");
	}
	
    public Location getLngAndLat(String address,String key){  
    	Location l = new Location();     	
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak="+key;  
        String json = loadJSON(url);  
        //JSONObject obj = JSONObject.fromObject(json);  
        try{  
            JSONObject obj = new JSONObject(json);  
            int status = (int)obj.get("status");
            if(status == 0){  
                double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");  
                double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");  
                l.setCode(Location.CODE_SUCCESS);                
                l.setLat(lat);
                l.setLng(lng);  
            }else if (obj.get("status").toString().equals("4")){ 
            	l.setCode(Location.CODE_FAILED);
            	l.setMsg(ERROR_4);
            } 
            else if (obj.get("status").toString().equals("101")){ 
            	l.setCode(Location.CODE_FAILED);
            	l.setMsg(ERROR_101);
            	keyholder.refreshAvailableKey();
            	return this.getLngAndLat(address);
            }else if (obj.get("status").toString().equals("1")) {
            	l.setCode(Location.CODE_FAILED);
            	l.setMsg(ERROR_1+":"+address);
            } else {
            	l.setCode(Location.CODE_FAILED);
            	l.setMsg(ERROR_DEFAULT);
            }
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return l;  
    }  
  
    public static String loadJSON (String url) {  
        StringBuilder json = new StringBuilder();  
        try {  
            URL oracle = new URL(url);  
            URLConnection yc = oracle.openConnection();  
            BufferedReader in = new BufferedReader(new InputStreamReader(  
                    yc.getInputStream()));  
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            in.close();  
        } catch (MalformedURLException e) {  
        } catch (IOException e) {  
        }  
        return json.toString();  
    }  

	public static void main(String[] args) throws IOException {
		BaiduLocationUtil util = new BaiduLocationUtil();
		ElevatorInfo ele = new ElevatorInfo();
//		ele.setBuildAddress("湖南省邵阳市邵东县龙腾龙村花园");
		ele.setBuildAddress("avc");
		Location l = util.getLngAndLat(ele.getBuildAddress());
		logger.debug(l.toString());
	}
}
