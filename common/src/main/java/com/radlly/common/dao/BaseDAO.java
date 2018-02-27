package com.radlly.common.dao;



import java.util.List;


public interface BaseDAO<M,QM>{
	
	public int create(M m);
	public int update(M m);
	public int delete(Long uuid);
	
	public M getByUuid(Long uuid);
	public List<M> getByConditionPage(QM qm);
	public List<M> getByCondition(QM qm);
}
