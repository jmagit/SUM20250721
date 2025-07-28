package com.example;

import javax.naming.NamingException;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import com.example.contracts.distributed.services.ConverterRemote;
import com.example.contracts.distributed.services.CortesiaRemote;
import com.example.contracts.distributed.services.CounterRemote;
import com.example.contracts.domain.distributed.FacturasCommand;
import com.example.contracts.domain.distributed.PedidosCommand;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import jakarta.jms.Topic;

@Configuration
public class JndiConfig {
	@Bean
	JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}

	@Bean
	CortesiaRemote cortesiaRemote(JndiTemplate jndiTemplate) throws NamingException {
		return (CortesiaRemote) jndiTemplate
				.lookup("java:global/demo-ejb/CortesiaBean!com.example.contracts.distributed.services.CortesiaRemote");
	}

//	@Bean
//	CounterRemote counterRemote(JndiTemplate jndiTemplate) throws NamingException {
//		return (CounterRemote) jndiTemplate
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
	ConverterRemote converterRemote(JndiTemplate jndiTemplate) throws NamingException {
		return (ConverterRemote) jndiTemplate
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
	
//	@Bean(destroyMethod = "")
//	public DataSource dataSource() throws Exception {
//	    JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//	    bean.setJndiName("java:/SakilaDataSource");
//        bean.setResourceRef(true); // Indica que es una referencia a un recurso de entorno
////        bean.setExpectedType(DataSource.class);
//        bean.setProxyInterface(DataSource.class);
//	    bean.afterPropertiesSet();
//	    return (DataSource) bean.getObject();
//	}
//	@Bean
//	public PlatformTransactionManager transactionManager() { 
//	    return new JtaTransactionManager(); 
//	}

	@Bean
	PedidosCommand pedidosCommand() throws NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:global/demo-ejb/PedidosQueue!com.example.contracts.domain.distributed.PedidosCommand");
		bean.setProxyInterface(PedidosCommand.class);
		bean.afterPropertiesSet();
		return (PedidosCommand) bean.getObject();
	}
	@Bean
	FacturasCommand facturasCommand() throws NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:global/demo-ejb/FacturasQueue!com.example.contracts.domain.distributed.FacturasCommand");
		bean.setProxyInterface(FacturasCommand.class);
		bean.afterPropertiesSet();
		return (FacturasCommand) bean.getObject();
	}
	
//	@Bean
//	public JndiObjectFactoryBean connectionFactory() {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		bean.setJndiName("java:/ConnectionFactory"); // Este es el nombre típico en WildFly
//		return bean;
//	}
//
//	@Bean
//	public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setConnectionFactory(connectionFactory);
//		return factory;
//	}
	@Bean
	JmsListenerContainerFactory<?> topicListenerContainerFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
//
//	@Bean
//	public ConnectionFactory jmsConnectionFactory() throws IllegalArgumentException {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		// El nombre JNDI de tu ConnectionFactory JMS configurado en el servidor
////        bean.setJndiName("java:/ConnectionFactory"); 
//		bean.setJndiName("java:/JmsXA"); // Ejemplo para WildFly
//		bean.setResourceRef(true);
//		bean.setExpectedType(ConnectionFactory.class);
//		return (ConnectionFactory) bean.getObject();
//	}
//
//	@Bean
//	public Queue peticionesQueue() throws IllegalArgumentException {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		// El nombre JNDI de tu cola JMS configurada en el servidor
//		bean.setJndiName("java:/jms/queue/peticiones");
//		bean.setResourceRef(true);
//		bean.setExpectedType(Queue.class);
//		return (Queue) bean.getObject();
//	}
//	
//    @Bean
//    public Queue respuestasQueue() throws IllegalArgumentException {
//        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//        // El nombre JNDI de tu cola JMS configurada en el servidor
//        bean.setJndiName("java:/jms/queue/respuestas"); 
//        bean.setResourceRef(true);
//        bean.setExpectedType(Queue.class);
//        return (Queue) bean.getObject();
//    }
//    
//    @Bean
//    public Topic sensoresQueue() throws IllegalArgumentException {
//        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//        // El nombre JNDI de tu tema JMS configurada en el servidor
//        bean.setJndiName("java:/jms/topic/sensores"); 
//        bean.setResourceRef(true);
//        bean.setExpectedType(Queue.class);
//        return (Topic) bean.getObject();
//    }
	
    
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
