package com.jerseyserver;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class RestApplication extends ResourceConfig {
	public RestApplication() {
		// 服务类所在的包路径
		packages("com.jerseyserver.service");
		// 注册JSON转换器
		register(JacksonJsonProvider.class);
		// 打印访问日志，便于跟踪调试，正式发布可清除
		register(LoggingFilter.class);
	}
}
