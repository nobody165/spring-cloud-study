package com.radlly.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radlly.model.User;

@FeignClient(name = "m-provider-user")
public interface UserFeignClient {
  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public User get(@PathVariable("id") Long id);
}
