package com.example;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import com.example.contracts.distributed.services.ConverterRemote;
import com.example.contracts.distributed.services.CortesiaRemote;
import com.example.contracts.distributed.services.CounterRemote;

@Configuration
public class EjbsConfig {
	@Bean
	CortesiaRemote cortesiaRemote() throws NamingException {
		return (CortesiaRemote) new JndiTemplate()
				.lookup("java:global/demo-ejb/CortesiaBean!com.example.contracts.distributed.services.CortesiaRemote");
	}
	@Bean
	ConverterRemote converterRemote() throws NamingException {
		return (ConverterRemote) new JndiTemplate()
				.lookup("java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote");
	}
	@Bean
	CounterRemote counterRemote() throws NamingException {
		return (CounterRemote) new JndiTemplate()
				.lookup("java:global/demo-ejb/CounterBean!com.example.contracts.distributed.services.CounterRemote");
	}
}
