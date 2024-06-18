package com.example.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyConfig {
	@Value("${app.contador.init:1}")
	int number;
	
	@Bean
	@Scope("prototype")
	Entorno entorno() {
		return new EntornoImpl(number);
	}

}
