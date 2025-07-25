package com.example;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import com.example.contracts.distributed.services.ConverterRemote;
import com.example.contracts.distributed.services.CortesiaRemote;
import com.example.contracts.distributed.services.CounterRemote;

@Configuration
public class JndiConfig {
	private JndiTemplate jndiTemplate;

	@Bean
	JndiTemplate jndiTemplate() {
		if (jndiTemplate == null)
			jndiTemplate = new JndiTemplate();
		return jndiTemplate;
	}

	@Bean
	CortesiaRemote cortesiaRemote() throws NamingException {
		return (CortesiaRemote) jndiTemplate()
				.lookup("java:global/demo-ejb/CortesiaBean!com.example.contracts.distributed.services.CortesiaRemote");
	}

//	@Bean
//	CounterRemote counterRemote() throws NamingException {
//		return (CounterRemote) jndiTemplate()
//				.lookup("java:global/demo-ejb/CounterBean!com.example.contracts.distributed.services.CounterRemote");
//	}
	@Bean
	CounterRemote counterRemote() throws NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:global/demo-ejb/CounterBean!com.example.contracts.distributed.services.CounterRemote");
		bean.setProxyInterface(CounterRemote.class);
		bean.afterPropertiesSet();
		return (CounterRemote) bean.getObject();
	}

	@Bean
	ConverterRemote converterRemote() throws NamingException {
		return (ConverterRemote) jndiTemplate()
				.lookup("java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote");
	}

//	@Bean
//	ConverterLocal converterLocal() throws NamingException {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		bean.setJndiName("java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterLocal");
//		bean.setProxyInterface(CounterRemote.class);
//		bean.afterPropertiesSet();
//		return (ConverterLocal) bean.getObject();
//	}
	
//	@Bean
//	public DataSource dataSource() throws Exception {
//	    JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//	    bean.setJndiName("java:/SakilaDataSource");
//        bean.setResourceRef(true); // Indica que es una referencia a un recurso de entorno
////        bean.setExpectedType(DataSource.class);
//        bean.setProxyInterface(DataSource.class);
//	    bean.afterPropertiesSet();
//	    return (DataSource) bean.getObject();
//	}
	
//    @Bean
//    public SimpleRemoteStatelessSessionProxyFactoryBean converterRemote() {
//        SimpleRemoteStatelessSessionProxyFactoryBean factory = new SimpleRemoteStatelessSessionProxyFactoryBean();
//        factory.setJndiName("java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote");
//        factory.setBusinessInterface(ConverterRemote.class);
//
//        Properties jndiProps = new Properties();
//        jndiProps.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
//        jndiProps.put("java.naming.provider.url", "http-remoting://localhost:8080");
//        // jndiProps.put("java.naming.security.principal", "username");
//        // jndiProps.put("java.naming.security.credentials", "password");
//        factory.setJndiEnvironment(jndiProps);
//
//        return factory;
//    }
}
