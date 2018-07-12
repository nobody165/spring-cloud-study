package com.radlly.configuration;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.radlly.model.ElevatorInfo;

import lombok.Data;

@Configuration
@Data
public class QuesesConfiguration {	
	
	@Qualifier("evQueue")
	@Bean
	public ArrayBlockingQueue<List<ElevatorInfo>> evQueue(){
		return new ArrayBlockingQueue<List<ElevatorInfo>>(5);
	}

}
