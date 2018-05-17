package com.radlly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radlly.mapper.ElevatorMapper;
import com.radlly.model.ElevatorInfo;

@Service
public class ElevatorInfoImpl implements IElevatorService{

	@Autowired
	private ElevatorMapper elevatorMapper;
	@Override
	public boolean save(ElevatorInfo elevatorInfo) {
		int result = elevatorMapper.insert(elevatorInfo);
		if(1==result)return true;
		return false;
	}

	@Override
	public ElevatorInfo get(long uuid) {
		// TODO Auto-generated method stub
		return elevatorMapper.get(uuid);
	}

}
