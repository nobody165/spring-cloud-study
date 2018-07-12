package com.radlly.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.radlly.model.ElevatorInfo;


public interface ElevatorMapper {
	
	int insert(ElevatorInfo e);
	
	ElevatorInfo get(long uuid);
	/**
	 * 
	 * @param elevatorInfo must extends BaseObject int pageStart,int pageEnd.
	 * @return
	 */
	List<ElevatorInfo> getPage(ElevatorInfo elevatorInfo);
		
	List<ElevatorInfo> findUseForEvs(@Param("usefor")String usefor,@Param("pageStart")int pageStart,@Param("pageEnd")int pageEnd);
	
	
	void insertBatch(@Param("list")List<ElevatorInfo> evs);

}
