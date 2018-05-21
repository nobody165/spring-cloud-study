package com.radlly.mapper;

import java.util.List;

import com.radlly.model.ElevatorInfo;

public interface ElevatorMapper {
	
	int insert(ElevatorInfo e);
	
	ElevatorInfo get(long uuid);
	
	List<ElevatorInfo> getPage(ElevatorInfo elevatorInfo);
	
	List<ElevatorInfo> findUseForEvs(String usefor);

}
