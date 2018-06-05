package com.radlly.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radlly.exception.RadllyException;
import com.radlly.mapper.ElevatorMapper;
import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;
import com.radlly.utils.JsonHelper;
import com.radlly.utils.JsonValidator;

@Service
public class ElevatorInfoImpl implements IElevatorService{
	private static Logger logger = LoggerFactory.getLogger(ElevatorInfoImpl.class);

	
	@Autowired
	private ElevatorMapper elevatorMapper;
	
	@Override
	public AppObj save(ElevatorInfo elevatorInfo) {				
			

		int result = elevatorMapper.insert(elevatorInfo);
		
		if(1==result)new AppObj(AppObj.SSUCCESS);
		return new AppObj("save faild, please contact with system manager!");
	}

	@Override
	public ElevatorInfo get(long uuid) {
		// TODO Auto-generated method stub
		return elevatorMapper.get(uuid);
	}

	@Override
	public List<ElevatorInfo> getPage(ElevatorInfo elevatorInfo) {
		// TODO Auto-generated method stub
		if(null==elevatorInfo) elevatorInfo = new ElevatorInfo();
		return elevatorMapper.getPage(elevatorInfo);
	}
	
	@Override
	public List<ElevatorInfo> findUseForEvs(String usefor,int pageStart,int pageEnd) {
		// TODO Auto-generated method stub
		return elevatorMapper.findUseForEvs(usefor,pageStart,pageEnd);
	}

	
	
	

}
