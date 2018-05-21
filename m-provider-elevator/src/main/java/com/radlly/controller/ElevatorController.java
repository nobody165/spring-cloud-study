package com.radlly.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.radlly.service.IElevatorService;

@RestController
public class ElevatorController {
	private static Logger logger = LoggerFactory.getLogger(ElevatorController.class);
	
	@Autowired
	private EvAttributes evAttributes;
	@Autowired
	private IElevatorService elevatorService;
	
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
		elevatorInfo.setCreateAt(new Date());
		if(!elevatorService.save(elevatorInfo))
		 return new AppObj(AppObj.FAIL);
		return new AppObj();
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
	
	@GetMapping("/ev/findBrandEvs")
	@ResponseBody
	public AppObj findBrandEvs(@RequestParam String usefor) {
		return new AppObj(elevatorService.findUseForEvs(usefor));
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
