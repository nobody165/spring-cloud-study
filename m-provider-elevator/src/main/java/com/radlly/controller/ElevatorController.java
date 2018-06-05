package com.radlly.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radlly.configuration.EvAttributes;
import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;
import com.radlly.model.Location;
import com.radlly.service.IElevatorService;
import com.radlly.utils.BaiduLocationUtil;

@RestController
public class ElevatorController {
	private static Logger logger = LoggerFactory.getLogger(ElevatorController.class);
	
	@Autowired
	private EvAttributes evAttributes;
	@Autowired
	private IElevatorService elevatorService;
	@Autowired
	private BaiduLocationUtil baiduLocationUtil;
	@GetMapping("/ev/attributes")
	@ResponseBody
	public AppObj attributes() {
		AppObj result = new AppObj();
		
		Map<String, String> attrs = evAttributes.getOrderedMap();
		result.setObj(attrs);
		return result;
	}
	
	@PostMapping("/ev/addEv")
	@ResponseBody
	public AppObj addEv(@RequestBody ElevatorInfo elevatorInfo) {	
		if(StringUtils.isBlank(elevatorInfo.getBuildAddress())) {
			logger.debug("save elevatorInfo faild, buildAddress is null : "+elevatorInfo);
			return new AppObj("buildAddress can not be null!");
		}
		if(null==elevatorInfo.getLatitude()||null==elevatorInfo.getLongitude()) {
			Location l = baiduLocationUtil.getLngAndLat(elevatorInfo.getBuildAddress());
			if(Location.CODE_SUCCESS==l.getCode()) {
				elevatorInfo.setLatitude(l.getLat());
				elevatorInfo.setLongitude(l.getLng());
			}else {
				logger.debug("save ev faild."+elevatorInfo.toString());
				return new AppObj("couldn't found location with lat:"+elevatorInfo.getLatitude()+" and lng:"+elevatorInfo.getLongitude());
			}
		}
		return elevatorService.save(elevatorInfo);
	}
	
	@GetMapping("/ev/get")
	@ResponseBody
	public AppObj get(@PathVariable Long uuid) {
		return new AppObj(elevatorService.get(uuid));
	}
	
	@PostMapping("/ev/getPage")
	@ResponseBody
	public AppObj getPage(@RequestBody(required = false) ElevatorInfo elevatorInfo) {
		return new AppObj(elevatorService.getPage(elevatorInfo));
	}
	
	
	@GetMapping("/ev/findUseForEvs")
	@ResponseBody
	public AppObj findUseForEvs(@RequestParam String usefor) {
		return new AppObj(elevatorService.findUseForEvs(usefor,0,10));
	}
	
	
	
	public static void main(String[] args) {
		 ObjectMapper mapper = new ObjectMapper();  
		 List<ElevatorInfo> l = new ArrayList<ElevatorInfo>();
		 l.add(new ElevatorInfo());
		 l.add(new ElevatorInfo());
		 l.add(new ElevatorInfo());
		 AppObj app = new AppObj(l);
	        try {
				System.out.println(mapper.writeValueAsString(app));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	}

}
