package com.radlly.mapper;

import java.util.Date;
import java.util.List;

import com.radlly.model.WorkOrder;

public interface WorkOrderMapper {
    
	 List<WorkOrder> queryList(Date startDate, Date endDate, Long companyId);
	 
	 List<Date> companyUsedMonth(Long companyId);
}