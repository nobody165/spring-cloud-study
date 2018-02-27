package com.radlly.common.service;



import java.util.List;

import com.radlly.common.dao.BaseDAO;
import com.radlly.common.vo.BaseModel;
import com.radlly.pageutil.Page;



public class BaseService<M, QM extends BaseModel> implements IBaseService<M,QM> {
	private BaseDAO dao = null;
	public void setDAO(BaseDAO dao){
		this.dao = dao;
	}
	public int create(M m) {
		return dao.create(m);
	}

	public int update(M m) {
		return dao.update(m);
	}

	public int delete(Long uuid) {
		return dao.delete(uuid);
	}

	public M getByUuid(Long uuid) {
		return (M)dao.getByUuid(uuid);
	}

	public Page<M> getByConditionPage(QM qm) {
		List<M> list = dao.getByConditionPage(qm);
		qm.getPage().setResult(list);
		
		return qm.getPage();
	}
}
