package com.radlly.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.radlly.common.vo.BaseModel;
import com.radlly.mysql.Snowflake;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class InsertInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		Object obj = invocation.getArgs()[1];
		if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
			if (obj instanceof BaseModel) {
				BaseModel oBaseModel = (BaseModel) obj;
				if(oBaseModel.getUuid() != null)
				{
					int b =oBaseModel.getUuid().intValue();
				}
				if (oBaseModel.getUuid() == null) {
					oBaseModel.setUuid(Snowflake.next());
				}
			}else if(obj instanceof HashMap)
			{
				ArrayList<BaseModel> alBaseModel = (ArrayList)((HashMap) obj).get("list");

				for(BaseModel baseModel:alBaseModel)
				{
					if (baseModel.getUuid() == null) {
						baseModel.setUuid(Snowflake.next());
					}
				}
//				((HashMap) obj).put("list", alBaseModel);
			}
		}

		Object obj2 = invocation.proceed();
		return obj2;
	}

	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
