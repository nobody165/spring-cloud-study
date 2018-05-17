package com.radlly.service;

import com.radlly.model.ElevatorInfo;

public interface IElevatorService {
	
	public boolean save(ElevatorInfo elevatorInfo);

	public ElevatorInfo get(long uuid);
}
