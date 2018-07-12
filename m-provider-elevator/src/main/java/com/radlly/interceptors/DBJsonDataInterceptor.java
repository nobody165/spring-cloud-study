package com.radlly.interceptors;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.radlly.configuration.SnowFlakeIdFactory;
import com.radlly.model.BaseObject;
import com.radlly.utils.Snowflake;

@Intercepts(value = {
		@Signature(args = { Connection.class, Integer.class }, method = "prepare", type = StatementHandler.class) })
public class DBJsonDataInterceptor implements Interceptor {
	private Logger logger = LoggerFactory.getLogger(DBJsonDataInterceptor.class);
	
	@Autowired
	private SnowFlakeIdFactory snowFlakeIdFactory;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("in InsertInterceptor###");
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql();
		Object obj = statementHandler.getParameterHandler().getParameterObject();
		if (sql.startsWith("insert")) {
			logger.debug("is insert invocation!");
			if (obj instanceof BaseObject) {
				BaseObject oBaseModel = (BaseObject) obj;
				if (oBaseModel.getUuid() == 0) {
					oBaseModel.setUuid(snowFlakeIdFactory.nextId());
				}
			} else if (obj instanceof HashMap) {
				ArrayList<BaseObject> alBaseModel = (ArrayList) ((HashMap) obj).get("list");

				for (BaseObject baseModel : alBaseModel) {
					if (baseModel.getUuid() == 0) {
						baseModel.setUuid(snowFlakeIdFactory.nextId());
					}
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}
}
