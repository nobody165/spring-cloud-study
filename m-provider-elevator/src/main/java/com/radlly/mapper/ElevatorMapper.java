package com.radlly.mapper;

import com.radlly.model.ElevatorInfo;

public interface ElevatorMapper {
	
	int insert(ElevatorInfo e);
	
	ElevatorInfo get(long uuid);

}
