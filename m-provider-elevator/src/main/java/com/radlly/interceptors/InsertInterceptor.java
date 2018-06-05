package com.radlly.interceptors;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.radlly.exception.RadllyException;
import com.radlly.model.BaseObject;
import com.radlly.utils.JsonHelper;
import com.radlly.utils.JsonValidator;
import com.radlly.utils.Snowflake;

@Intercepts(value={@Signature(args={ Connection.class, Integer.class},method="prepare",
type=StatementHandler.class) })
public class InsertInterceptor implements Interceptor{
	private Logger logger = LoggerFactory.getLogger(InsertInterceptor.class);
	
	@Autowired
	private JsonValidator jsonValidator;
	@Autowired
	private JsonHelper jsonHelper;
	
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("in InsertInterceptor###");
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();      
        String sql = boundSql.getSql();
		Object obj =   statementHandler.getParameterHandler().getParameterObject();
		if (sql.startsWith("insert")) {
			logger.debug("is insert invocation!");
			if (obj instanceof BaseObject) {
				BaseObject oBaseModel = (BaseObject) obj;
				if (oBaseModel.getUuid() == 0) {
					oBaseModel.setUuid(Snowflake.next());
				}
				if(null!=oBaseModel.getObj()||StringUtils.isNoneBlank(oBaseModel.getJsonObj()))
					setJsonStr(oBaseModel);
			}else if(obj instanceof HashMap)
			{
				ArrayList<BaseObject> alBaseModel = (ArrayList)((HashMap) obj).get("list");

				for(BaseObject baseModel:alBaseModel)
				{
					if (baseModel.getUuid() == 0) {
						baseModel.setUuid(Snowflake.next());
					}
					if(null!=baseModel.getObj()||StringUtils.isNoneBlank(baseModel.getJsonObj()))
						setJsonStr(baseModel);
				}
			}
		}
		
		
		//set jsonObj
		
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
	
	private void setJsonStr(BaseObject oBaseModel) {
		if(null == oBaseModel.getObj()) {
			oBaseModel.setObj(jsonHelper.createJsonValue("createAt", new Date()));
		}else oBaseModel.setObj(jsonHelper.addJsonValue(oBaseModel.getObj().toString(), "createAt", new Date()));
		
		if(null==oBaseModel.getJsonObj()||!jsonValidator.validate(oBaseModel.getJsonObj())) {			
			if(null!=oBaseModel.getObj()&&jsonValidator.validate(oBaseModel.getObj().toString())) {
				oBaseModel.setJsonObj(oBaseModel.getObj().toString());
			}
			else {
				throw new RadllyException("jsonObj or obj  <can not be null or it is not json string>");
			}
		}
	}

}
