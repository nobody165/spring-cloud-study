package com.radlly.configuration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
@EnableTransactionManagement
public class DruidConfig {
	@Autowired
	private DatasourcePorperties datasourcePorperties;

	@Bean
	@Qualifier("druidDataSource")
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(datasourcePorperties.getUrl());
		datasource.setDriverClassName(datasourcePorperties.getDriverClassName());
		datasource.setUsername(datasourcePorperties.getUsername());
		datasource.setPassword(datasourcePorperties.getPassword());
		datasource.setInitialSize(Integer.valueOf(datasourcePorperties.getInitialSize()));
		datasource.setMinIdle(Integer.valueOf(datasourcePorperties.getMinIdle()));
		datasource.setMaxWait(Long.valueOf(datasourcePorperties.getMaxWait()));
		datasource.setMaxActive(Integer.valueOf(datasourcePorperties.getMaxActive()));
		datasource.setMinEvictableIdleTimeMillis(Long.valueOf(datasourcePorperties.getMinEvictableIdleTimeMillis()));
		try {
			datasource.setFilters("stat,wall");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datasource;
	}

	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		// initParameters.put("loginUsername", "druid");// 鐢ㄦ埛鍚�
		// initParameters.put("loginPassword", "druid");// 瀵嗙爜
		initParameters.put("resetEnable", "false");// 绂佺敤HTML椤甸潰涓婄殑鈥淩eset All鈥濆姛鑳�
		initParameters.put("allow", "172.16.3.4"); // IP鐧藉悕鍗� (娌℃湁閰嶇疆鎴栬�呬负绌猴紝鍒欏厑璁告墍鏈夎闂�)
		// initParameters.put("deny", "192.168.20.38");// IP榛戝悕鍗�
		// (瀛樺湪鍏卞悓鏃讹紝deny浼樺厛浜巃llow)
		servletRegistrationBean.setInitParameters(initParameters);
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	@Bean(value = "druid-stat-interceptor")
	public DruidStatInterceptor DruidStatInterceptor() {
		DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
		return druidStatInterceptor;
	}

	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
		return beanNameAutoProxyCreator;
	}
}
