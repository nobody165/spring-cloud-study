package com.radlly.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.radlly.configuration.EvAttributes;
import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;
import com.radlly.service.IElevatorService;
import com.radlly.utils.StringUtil;

@RestController
public class ElevatorController {
	
	@Autowired
	private EvAttributes evAttributes;
	@Autowired
	private IElevatorService elevatorService;
	
	@GetMapping("/ev/attributes")
	@ResponseBody
	public AppObj attributes() {
		AppObj result = new AppObj();
		
		Map<String, String> attrs = evAttributes.getAttributes();
		result.setObj(attrs);
		return result;
	}
	
	@PostMapping("/ev/addEv")
	@ResponseBody
	public AppObj addEv(@RequestBody ElevatorInfo elevatorInfo) {		
		if(!elevatorService.save(elevatorInfo))
		 return new AppObj(AppObj.FAIL);
		return new AppObj();
	}
	
	@GetMapping("/ev/get")
	@ResponseBody
	public AppObj get(@PathVariable Long uuid) {
		return new AppObj(elevatorService.get(uuid));
	}
	
	public static void main(String[] args) {
		HashMap map = new HashMap();
		map.put("usefor", "乘客电梯");
		map.put("inspectionOrg", "成都质检");
		map.put("deviceNumber", "55#444");
		map.put("manufacturer", "奥的斯电梯");
		  String str = JSON.toJSONString(map);  
	        System.out.println(str);  
	}

}
