package com.radlly.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radlly.configuration.SnowFlakeIdFactory;
import com.radlly.exception.RadllyException;
import com.radlly.mapper.ElevatorMapper;
import com.radlly.model.AppObj;
import com.radlly.model.ElevatorInfo;
import com.radlly.utils.JsonHelper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ElevatorInfoImpl implements IElevatorService{
	@Autowired
	private JsonHelper jsonHelper;
	@Autowired
	private ElevatorMapper elevatorMapper;
	@Autowired 
	@Qualifier("batchJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SnowFlakeIdFactory snowFlakeIdFactory;
	
	@Override
	public AppObj save(ElevatorInfo elevatorInfo) {				
			
		elevatorInfo.setCreateAt(new Date());
		if(null!=elevatorInfo.getObjArrs())
		elevatorInfo.setObj(jsonHelper.beanToJson(elevatorInfo.getObjArrs()));
		int result = elevatorMapper.insert(elevatorInfo);
		
		if(1!=result) {
			log.debug("couldn't save into database, please check connection!");
			throw new RadllyException("save elevator faild, please try again later!");			
		}
		return new AppObj(AppObj.SSUCCESS);
	}

	@Override
	public ElevatorInfo get(long uuid) {
		// TODO Auto-generated method stub
		return elevatorMapper.get(uuid);
	}

	@Override
	public List<ElevatorInfo> getPage(ElevatorInfo elevatorInfo) {
		// TODO Auto-generated method stub
		if(null==elevatorInfo) elevatorInfo = new ElevatorInfo();
		return elevatorMapper.getPage(elevatorInfo);
	}
	
	@Override
	public List<ElevatorInfo> findUseForEvs(String usefor,int pageStart,int pageEnd) {
		// TODO Auto-generated method stub
		return elevatorMapper.findUseForEvs(usefor,pageStart,pageEnd);
	}

	@Override
	public void insertBatch(List<ElevatorInfo> evs) {
		 long start = System.currentTimeMillis();
		log.debug("start: "+start);
		elevatorMapper.insertBatch(evs);
		log.debug("cost: "+(System.currentTimeMillis()-start));
	}
	@Transactional()  
	public void batchInsertJDBC(List<ElevatorInfo> evs) throws DataAccessException {  
//		insert into ev_info (uuid,propertyCom, buildAddress, latitude, longitude, evCode, regCode, evOrder,brand,evType,createAt,del,obj)
//	    values (#{uuid},#{propertyCom}, #{buildAddress}, #{latitude},#{longitude}, #{evCode}, #{regCode}, 
//	      #{evOrder},#{brand},#{evType},#{createAt},#{del},CONVERT(#{jsonObj} using utf8mb4))GeomFromText(''{1}'')
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    StringBuffer sqlbuf = new StringBuffer()  
	        .append("insert into ev_info (uuid,propertyCom, buildAddress, evCode, regCode, evOrder,brand,evType,createAt,del,obj) values ");  
	    MessageFormat form = new MessageFormat("(''{0}'', ''{1}'', ''{2}'', ''{3}'', ''{4}'', ''{5}'', ''{6}'', ''{7}'', ''{8}'', ''{9}'',"
	    		+ "CONVERT( (''{10}'') using utf8mb4)),");  
	    for (ElevatorInfo ev : evs) {  
	        Object[] args = {String.valueOf(snowFlakeIdFactory.nextId()), ev.getPropertyCom(), ev.getBuildAddress(), 
	        		ev.getEvCode(), ev.getRegCode(), ev.getEvOrder(),ev.getBrand(),ev.getEvType(),
	        		simpleDateFormat.format(ev.getCreateAt()),ev.getDel(),ev.getObj()};  
	        sqlbuf.append(form.format(args));  
	    }  
	    String sql = sqlbuf.toString();  
	    sql = sql.substring(0, sql.length()-1);  
	    long start = System.currentTimeMillis();
	    jdbcTemplate.update(sql);  
	    log.debug("cost: "+(System.currentTimeMillis()-start));
	}
	

	

}
