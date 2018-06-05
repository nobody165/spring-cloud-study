package com.radlly;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.radlly.model.ElevatorInfo;
import com.radlly.service.IElevatorService;
import com.radlly.utils.JsonHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
public class ElevatorTest {
	private static Logger logger = LoggerFactory.getLogger(ElevatorTest.class);
	@Autowired
	private IElevatorService  elevatorService ;
	@Autowired
	private JsonHelper jsonHelper;
	@Test
	public void TestSave() throws IOException {
		ElevatorInfo info = new ElevatorInfo();
		info.setObj(jsonHelper.createJsonValue("usefor", "工业用梯"));
		info.setBuildAddress("四川省成都市武侯区长虹科技大厦");
		elevatorService.save(info);
	}
	
//	@Test
	public void TestGet() throws IOException {
		List<ElevatorInfo> evs = elevatorService.findUseForEvs("工业用梯",0,10);
		for(ElevatorInfo info : evs) {
			logger.debug(info.toString());
		}
	}
}
