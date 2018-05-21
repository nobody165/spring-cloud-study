package com.radlly.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radlly.mapper.ElevatorMapper;
import com.radlly.model.ElevatorInfo;
import com.radlly.model.Location;
import com.radlly.utils.BaiduLocationUtil;

@Service
public class ElevatorInfoImpl implements IElevatorService{
	private static Logger logger = LoggerFactory.getLogger(ElevatorInfoImpl.class);

	@Autowired
	private ElevatorMapper elevatorMapper;
	@Autowired
	private BaiduLocationUtil baiduLocationUtil;
	@Override
	public boolean save(ElevatorInfo elevatorInfo) {
		if(StringUtils.isBlank(elevatorInfo.getBuildAddress())) {
			logger.debug("save elevatorInfo faild, buildAddress is null : "+elevatorInfo);
			return false;
		}
		if(null==elevatorInfo.getLatitude()||null==elevatorInfo.getLongitude()) {
			Location l = baiduLocationUtil.getLngAndLat(elevatorInfo.getBuildAddress());
			if(Location.CODE_SUCCESS==l.getCode()) {
				elevatorInfo.setLatitude(l.getLat());
				elevatorInfo.setLongitude(l.getLng());
			}
		}
		int result = elevatorMapper.insert(elevatorInfo);
		if(1==result)return true;
		return false;
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
	public List<ElevatorInfo> findUseForEvs(String usefor) {
		// TODO Auto-generated method stub
		return elevatorMapper.findUseForEvs(usefor);
	}
	
	
	

}
