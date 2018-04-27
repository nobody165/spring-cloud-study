package com.radlly.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClearingAbleHashMap {
	
	private static Logger logger = LoggerFactory.getLogger(ClearingAbleHashMap.class);
	
	Timer timer;
	
	private final CacheEntity defaultValue;
	
	private int cleaningTime;// in seconds
	
	private ConcurrentHashMap<Object,CacheEntity> map;

	public ClearingAbleHashMap(int cleaningTime) {
		super();
		this.cleaningTime = cleaningTime;
		map = new ConcurrentHashMap<Object, CacheEntity>();
		defaultValue = new CacheEntity("defualt cacheEntity");
		run(cleaningTime);
	}
	
	public void run(long time) {
		 timer = new Timer();
         timer.schedule(new TimerTask(){
             public void run(){
            	 logger.debug("do clean...."+LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            	 doClean();
             }
         },10*1000, this.cleaningTime*1000);
	}

	public int getCleaningTime() {
		return cleaningTime;
	}

	public void setCleaningTime(int cleaningTime) {
		this.cleaningTime = cleaningTime;
	}

	public CacheEntity put(Object key,CacheEntity value) {
		return map.putIfAbsent(key, value);		
	}
	
	public CacheEntity get(Object key) {		
		return map.getOrDefault(key, defaultValue);		
	}
	
	private void doClean() {
		 for (Entry<Object, CacheEntity> entry : map.entrySet()) {
			 CacheEntity c = entry.getValue();
             long millisStoreTime = entry.getValue().getStoreTime();
             long v = System.currentTimeMillis()-millisStoreTime;
             if(v<=c.getKeepAliveTime()?true:false) {            	 
            	 map.remove(entry.getKey());
            	 logger.debug("remove key:"+entry.getKey()+",storeTime:"+DateUtils.transferLongToDate(DateUtils.DATE_FORMAT_DATETIME, millisStoreTime));
            	 logger.debug("currentTime:"+LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
             }             
         }
	}
	
	public void cacel() {
		timer.cancel();
	}
	
	public static void main(String[] args) throws InterruptedException {
		String value ="testValue";
		int keyKoller = 0;
		ClearingAbleHashMap map = new ClearingAbleHashMap(10);
		map.put(keyKoller, new CacheEntity(value+keyKoller));			
		 logger.debug("put map :"+map.get(keyKoller));
		 keyKoller++;			
		Thread.sleep(5*1000);
		map.put(keyKoller, new CacheEntity(value+keyKoller));		
		 logger.debug("put map :"+map.get(keyKoller));
		 keyKoller++;	
		Thread.sleep(10*1000);
		
		map.put(keyKoller, new CacheEntity(value+keyKoller));		
		 logger.debug("put map :"+map.get(keyKoller));
		 keyKoller++;	
		while(true) {
			
		}
	}
	
}

class CacheEntity{
	
	private static Logger logger = LoggerFactory.getLogger(CacheEntity.class);
	
	private final long DEFAULT_KEEPALIVE_INMILLISECONDS = 30*1000;
	
	private long  storeTime;	
	
	private long keepAliveTime;// in milliseconds
	
	private Object value;

	public CacheEntity(Object value) {
		super();		
		setValue(value);
		keepAliveTime = DEFAULT_KEEPALIVE_INMILLISECONDS;
	}	

	/**
	 * 
	 * @param keepAliveTime milliseconds
	 * @param value
	 */
	public CacheEntity(long keepAliveTime, Object value) {
		super();
		this.keepAliveTime = keepAliveTime;
		this.value = value;
	}



	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.storeTime = System.currentTimeMillis();
		this.value = value;
	}

	public long getStoreTime() {
		return storeTime;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	@Override
	public String toString() {
		return "CacheEntity [ storeTime="+ DateUtils.transferLongToDate(DateUtils.DATE_FORMAT_DATETIME, storeTime) 
		+ ", keepAliveTime=" + keepAliveTime + ", value=" + value + "]";
	}


	
	
}


