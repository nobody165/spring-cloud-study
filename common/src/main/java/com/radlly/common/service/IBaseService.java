package com.radlly.common.service;

import com.radlly.common.vo.BaseModel;
import com.radlly.pageutil.Page;


public interface IBaseService<M,QM extends BaseModel> {
	public int create(M m);
	public int update(M m);
	public int delete(Long uuid);
	
	public M getByUuid(Long uuid);
	public Page<M> getByConditionPage(QM qm);
}
