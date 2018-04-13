package com.radlly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.radlly.feign.UserFeignClient;
import com.radlly.model.User;

@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	  @Autowired
	  private UserFeignClient userFeignClient;
//	  @Autowired
//	  private RestTemplate restTemplate;
	  @Autowired
	  private LoadBalancerClient loadBalancerClient;

	  @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
			  @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000"),
			  @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="10000")
	  },threadPoolProperties= {
			  @HystrixProperty(name="coreSize",value="1"),
			  @HystrixProperty(name="maxQueueSize",value="10")
	  })
	  @GetMapping("/user/{id}")
	  public User findById(@PathVariable Long id) {
	    return userFeignClient.get(id);
//		  return restTemplate.getForObject("http://m-provider-user/get/"+id, User.class);
	  }
	  
	  public User findByIdFallback(Long id){
		  User u = new User();
		  u.setUsername("出错啦！");
		  u.setUuid(-1L);
		  return u;
	  }
	  @GetMapping("/log-user-instance")
	  public void logUserInstance() {
	    ServiceInstance serviceInstance = this.loadBalancerClient.choose("m-provider-user");
	    // 打印当前选择的是哪个节点
	    logger.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
	  }

}
