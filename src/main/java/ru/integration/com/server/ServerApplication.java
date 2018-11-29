package ru.integration.com.server;

import org.gwtopenmaps.openlayers.server.GwtOpenLayersProxyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.integration.com.server")
public class ServerApplication {

	final static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new GwtOpenLayersProxyServlet(),"/olproxy/*");
	}

	@Autowired
	Environment env;

	/**
	 * entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
}
