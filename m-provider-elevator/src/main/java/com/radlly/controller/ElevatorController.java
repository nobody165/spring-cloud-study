package com.radlly.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
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
import com.radlly.exception.RadllyException;
import com.radlly.exception.RadllyExceptionCons;
import com.radlly.exception.RadllyExceptionErrorCode;
import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;
import com.radlly.model.Location;
import com.radlly.service.IElevatorService;
import com.radlly.taskExcutor.EvInfoInsert;
import com.radlly.utils.BaiduLocationUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags= {"elevator service"})
@RestController
public class ElevatorController {
	private static Logger logger = LoggerFactory.getLogger(ElevatorController.class);
	
	@Autowired
	private EvAttributes evAttributes;
	@Autowired
	private IElevatorService elevatorService;
	@Autowired
	private BaiduLocationUtil baiduLocationUtil;	
	@Autowired
	private EvInfoInsert evInfoInsert;
	
	@ApiOperation(value="获取系统支持的所有电梯属性",tags= {"电梯属性列表"},notes="eg: \"usefor\": \"用途\"")
	@GetMapping("/ev/attributes")
	@ResponseBody
	public AppObj attributes() {
		AppObj result = new AppObj();
		
		Map<String, String> attrs = evAttributes.getOrderedMap();
		result.setObj(attrs);
		return result;
	}
	
	@ApiOperation(value="根据系统支持的电梯属性进行增加",tags= {"新增电梯"},notes="如若经纬度没有将根据buildAddress获取,经纬度未获取禁止入库!")
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
	
	@ApiOperation(value="根据电梯ID获取电梯信息",tags={"根据电梯ID获取电梯信息"},notes="uuid加在path后 eg: {host}/ev/get/00001")
	@GetMapping("/ev/get")
	@ResponseBody
	public AppObj get(@ApiParam(name="evId",value="系统提供的电梯ID")@RequestParam String evId) {
		AppObj result = new AppObj(AppObj.SSUCCESS);
		Long uuid;
		try {
			 uuid = Long.valueOf(evId);
			 result.setObj(elevatorService.get(uuid));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(AppObj.FAIL);
			result.setMessage(RadllyExceptionCons.EV_GET_ERROR.getMessage());		
		}
		
		return result;
	}
	@ApiOperation(value="分页获取电梯列表",tags={"分页获取电梯列表"},notes="默认0-10条")
	@PostMapping("/ev/getPage")
	@ResponseBody
	public AppObj getPage(@RequestBody(required = false) ElevatorInfo elevatorInfo) {
		return new AppObj(elevatorService.getPage(elevatorInfo));
	}
	
	@ApiIgnore("暂停使用")
	@ApiOperation(value="根据用途获取电梯列表",tags={"根据用途获取电梯列表"},notes="默认0-10条")
	@GetMapping("/ev/findUseForEvs")
	@ResponseBody
	public AppObj findUseForEvs(@ApiParam(name="usefor",value="根据电梯用途获取电梯,0-10条")@RequestParam String usefor) {
		return new AppObj(elevatorService.findUseForEvs(usefor,0,10));
	}
	
	@ApiIgnore("暂停使用")
	@ApiOperation(value="测试多线程新增电梯,5000条",tags={"测试多线程新增电梯"},notes="队列,线程")
	@GetMapping("/ev/testBatch")
	@ResponseBody
	public AppObj testBatch() {
		ArrayBlockingQueue<List<ElevatorInfo>> q = new ArrayBlockingQueue<List<ElevatorInfo>>(5);	
		evInfoInsert.evInfoProducer(q, 50000);	
		evInfoInsert.evInfoConsumer(q);
		
		return new AppObj();
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
