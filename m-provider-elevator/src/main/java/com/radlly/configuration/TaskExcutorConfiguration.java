package com.radlly.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@ComponentScan("com.radlly.taskExcutor")
public class TaskExcutorConfiguration implements AsyncConfigurer{
	
	@Override
    public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);//线程池大小
            taskExecutor.setMaxPoolSize(10);//线程池最大线程数
            taskExecutor.setQueueCapacity(25);//最大等待任务数
            taskExecutor.initialize();
            return taskExecutor;
    }

   @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return null;
   }
}
