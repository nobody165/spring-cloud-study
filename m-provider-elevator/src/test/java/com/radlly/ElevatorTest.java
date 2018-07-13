package com.radlly;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.radlly.model.ElevatorInfo;
import com.radlly.service.IElevatorService;
import com.radlly.taskExcutor.EvInfoInsert;
import com.radlly.utils.JsonHelper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
@WebAppConfiguration
@Log4j2
public class ElevatorTest {
	@Autowired
	private IElevatorService elevatorService;
	@Autowired
	private JsonHelper jsonHelper;
	@Autowired
	private EvInfoInsert evInfoInsert;	 
	// @Test
	public void TestSave() throws IOException {
		ElevatorInfo info = new ElevatorInfo();
		info.setBrand("奥的斯");
		info.setBuildAddress("长虹科技大厦");
		info.setDel(ElevatorInfo.DEL_FALSE);
		info.setEvCode("2213sss222");
		info.setEvOrder("#1");
		info.setEvType("直梯");
		info.setPropertyCom("长虹物业");
		info.setRegCode("444432432342354353636");
		info.setJsonObj(jsonHelper.appendJsonValueStr("usefor", "工业用梯"));
		info.setJsonObj(jsonHelper.appendJsonValueStr(info.getJsonObj(), "maxLoad", "100"));
		info.setCreateAt(new Date());
		info.setBuildAddress("四川省成都市武侯区长虹科技大厦");
		elevatorService.save(info);
	}

	@Test 	
	public void TestInsertBatch(){	
		ArrayBlockingQueue<List<ElevatorInfo>> q = new ArrayBlockingQueue<List<ElevatorInfo>>(5);	
		
//		EvInfoProducerThread evProducer = new EvInfoProducerThread(500000);
//		EvInfoConsumerTread evConsumer = new EvInfoConsumerTread(evqueue,elevatorService);
//		Thread t1=new Thread(evProducer);
//		Thread t2=new Thread(evConsumer);
//		t1.start();
//		t2.start();
		
		
		evInfoInsert.evInfoProducer(q,50000);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		evInfoInsert.evInfoConsumer(q);
	
		try {
            log.info("begin to deal other Task!");
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	
	

	
	
	public static void main(String[] args) {
		
	}

	// @Test
	public void TestGet() throws IOException {
		List<ElevatorInfo> evs = elevatorService.findUseForEvs("工业用梯", 0, 10);
		for (ElevatorInfo info : evs) {
			log.debug(info.toString());
		}
	}
}
