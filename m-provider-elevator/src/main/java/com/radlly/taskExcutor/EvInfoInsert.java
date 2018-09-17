package com.radlly.taskExcutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.radlly.model.ElevatorInfo;
import com.radlly.service.IElevatorService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EvInfoInsert {
	@Autowired
	private IElevatorService elevatorService;

	@Async
	public void tt() {
		for (int i = 0; i < 50000; i++) {
			log.debug("=======:" + i);
		}
	}

	@Async
	public void evInfoProducer(ArrayBlockingQueue<List<ElevatorInfo>> q, int productTotal) {
		List<ElevatorInfo> evs = new ArrayList<ElevatorInfo>();
		String str = "{\"usefor\": \"乘客电梯\",\"inspectionOrg\": \"成都质检\",\"deviceNumber\": \"55#444\",\"manufacturer\": \"奥的斯\","
				+ "\"productionDate\":\"2018-08-31\",\"productionNumber\": \"111111\",\"modelNumber\": \"33321#11\",\"nominalSpeed\": \"20\","
				+ "\"nominalWidth\": \"33321#11\",\"inclinationAngle\": \"33321#11\",\"transforAbility\": \"33321#11\",\"liftingHeight\": \"33321#11\","
				+ "\"length\": \"1111\",\"power\": \"22\",\"floors\": \"33321#11\",\"station\": \"33321#11\",\"door\": \"33321#11\","
				+ "\"maxLoad\": \"333\",\"speedUp\": \"3\",\"speedDown\": \"33321#11\",\"hydraulicCylinder\": \"液压\","
				+ "\"jackingType\": \"T1\",\"actuationForm\": \"33321#11\",\"speed\": \"20M/s\"}";
		for (int i = 0; i < productTotal; i++) {
			ElevatorInfo info = new ElevatorInfo();
			info.setBrand("富士达");
			info.setBuildAddress("银泰城");
			info.setDel(ElevatorInfo.DEL_FALSE);
			info.setEvCode("22222222");
			info.setEvOrder("#" + i);
			info.setEvType("直梯");
			info.setPropertyCom("银泰物业");
			info.setRegCode("1111111111");
			info.setCreateAt(new Date());
			info.setObj(str);
			evs.add(info);
			if (i % 1000 == 0) {
				log.debug("add to queue" + evs.size());
				try {
					q.put(evs);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				evs.clear();
			}
		}
		log.debug("ev add finish...");

	}

	@Async
	public void evInfoConsumer(ArrayBlockingQueue<List<ElevatorInfo>> q) {
		ExecutorService consumerPool = Executors.newFixedThreadPool(5);
		ThreadPoolExecutor tpe = ((ThreadPoolExecutor) consumerPool);
		int finishTime = 0;
		long lastEmptyTime = 0;
		while (true) {
			try {
				int queueSize = tpe.getQueue().size();
				int activeCount = tpe.getActiveCount();
				long completedTaskCount = tpe.getCompletedTaskCount();
				long taskCount = tpe.getTaskCount();
				log.debug("排队线程数：" + queueSize + "活动线程数：" + activeCount + "完成线程数：" + completedTaskCount + "总线程数："
						+ taskCount);

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (q.isEmpty()) {
				if (finishTime == 5) {
					log.debug("q:" + q.isEmpty());
					log.debug("finish home work no more evinfo to insert!");
					consumerPool.shutdown();
					break;
				}
				try {
					log.debug("wait producer add ev!");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (lastEmptyTime > 0) {
					Long cost = System.currentTimeMillis() - lastEmptyTime;
					// 10秒计时器
					if (cost > 1000 * 10) {
						lastEmptyTime = 0;
						finishTime = 0;
					} else {
						finishTime++;
					}
				}
				lastEmptyTime = System.currentTimeMillis();
			} else {
				log.debug("待处理电梯列表:" + q.size());
				consumerPool.submit(new Runnable() {
					@Override
					public void run() {
						elevatorService.batchInsertJDBC((List<ElevatorInfo>) q.poll());
					}
				});				
			}

		}

	}
}
