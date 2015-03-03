package com.bta.twitter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.bta.twitter")
//@ImportResource({"classpath:/conf/services/transco/config-services.xml",
//				"classpath:/conf/component/transco/config-component.xml"})
public class SpringConfig {
}