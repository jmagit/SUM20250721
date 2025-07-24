package com.example;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import com.example.ejb.CortesiaRemote;

@Configuration
public class EjbsConfig {
	@Bean
	CortesiaRemote cortesiaRemote() throws NamingException {
		return (CortesiaRemote) new JndiTemplate().lookup("java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote");
	}
}
