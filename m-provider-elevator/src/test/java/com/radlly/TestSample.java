package com.radlly;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.radlly.mapper.ElevatorMapper;
import com.radlly.model.ElevatorInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
public class TestSample {
	private static Logger logger = LoggerFactory.getLogger(TestSample.class);

	@Autowired
	private SqlSessionFactory  SqlSessionFactory ;
	@Test
	public void TestOne() throws IOException {
		SqlSession session = SqlSessionFactory.openSession();
		ElevatorMapper em = session.getMapper(ElevatorMapper.class);
		ElevatorInfo ev = em.get(1);
		logger.debug(ev.toString());
	}
}
