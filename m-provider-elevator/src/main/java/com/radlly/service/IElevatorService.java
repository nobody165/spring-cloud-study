package com.radlly.service;

import java.util.List;

import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;

public interface IElevatorService {
	
	public AppObj save(ElevatorInfo elevatorInfo);
	
	public void insertBatch(List<ElevatorInfo> evs);

	public ElevatorInfo get(long uuid);
	
	public List<ElevatorInfo> getPage(ElevatorInfo elevatorInfo);
	
	
	public List<ElevatorInfo> findUseForEvs(String usefor,int pageStart,int pageEnd);
	
	public void batchInsertJDBC(List<ElevatorInfo> evs);
	
}
