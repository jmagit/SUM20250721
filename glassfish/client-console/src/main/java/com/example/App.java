package com.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.contracts.distributed.services.ConverterBeanRemote;
import com.example.contracts.distributed.services.CounterBean;
import com.example.contracts.distributed.services.LikesBeanRemote;
import com.example.contracts.distributed.services.SaludoBeanRemote;

public class App {
	public static void main(String[] args) {
// 		if (args.length > 0) {
// 			System.out.println(args[0] + " " + args[1]);
// 			switch (args[0]) {
// 			case "tienda":
// 				recibeFacturas(args[1]);
// 				mandaPedidos(args[1]);
// 				if (connectionListener != null)
// 					try {
// 						connectionListener.stop();
// 					} catch (JMSException e) {
// 						e.printStackTrace();
// 					}
// 				break;
// 			case "oficina":
// 				procesaPedidos(args[1]);
// 				break;
// 			case "sensor":
// 				for (int i = 1; i <= Integer.parseInt(args[1]); i++)
// 					try {
// 						final String name = Integer.toString(i);
// 						(new Thread(() -> publicador(name))).start();
// 						Thread.sleep(5000);
// 					} catch (InterruptedException e) {
// 						e.printStackTrace();
// 					}
// //				publicador(args[1]);
// 				break;
// 			case "calc":
// 				try {
// 					suscriptor(args[0] + "-" + args[1]);
// 					Thread.sleep(360000);
// 				} catch (InterruptedException e) {
// 					e.printStackTrace();
// 				}
// 				break;
// 			default:
// 				break;
// 			}
// 		}
		peticionesJNDI();
//		peticionesEJB();
		System.err.println("------------------------------- FIN");
	}

	private static void peticionesJNDI() {
		System.out.println("Peticiones JNDI");
		String jndi = "";
		var nombres = List.of(
				"java:comp/UserTransaction",
				"java:global/demos-ejb/CortesiaBean!com.example.ejb.CortesiaRemote",
				"java:global/demos-ejb/CortesiaBean",
				"java:global/ear-module/ejb-module/ConverterBean!com.example.contracts.distributed.services.ConverterBeanRemote", 
				"java:global/ear-module/ejb-module/ConverterBean!com.example.presentation.services.enterprise.ConverterBean", 
				"java:global/ear-module/ejb-module/ConverterBean!com.example.contracts.distributed.services.ConverterBeanLocal"
				);
		
		try {
			InitialContext ctx = getInitialContext(); // new InitialContext();
			System.out.println("Environment" + ctx.getNameInNamespace());
			ctx.getEnvironment().forEach((k, v) -> System.out.println(k + ": " + v));
			for(String nombre : nombres) {
				try {
					System.out.println("\nPeticion: " + nombre);
					Object ejb = null;
					ejb = ctx.lookup(nombre);
					System.out.println(ejb == null ? "No encontrado" : ("Encontrado:" + ejb.getClass().getCanonicalName()));
				} catch (Throwable e) {
					System.err.println("Error al buscar " + nombre + ": " + e.getMessage());
				}
			}
		} catch (NamingException e) {
			System.out.println("Error Environment");
//			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private static void peticionesEJB() {
		System.out.println("Peticiones EJB");
		String jndi = "";
		try {
			InitialContext ctx = new InitialContext();
			System.out.println("Environment" + ctx.getNameInNamespace());
			ctx.getEnvironment().forEach((k, v) -> System.out.println(k + ": " + v));
			System.out.println("Peticion Saluda");
			SaludoBeanRemote saluda = (SaludoBeanRemote) ctx
					.lookup("com.example.contracts.distributed.services.SaludoBeanRemote");
//			SaludoBeanLocal saluda = (SaludoBeanLocal) ctx.lookup("com.example.contracts.distributed.services.SaludoBeanLocal");
			System.out.println(saluda.getSaludo());
//			System.out.println(saluda.getSaludoFormal()); 
//			System.out.println(saluda.getSaludoInformal()); 

			System.out.println("Peticion ConverterBeanRemote");
			jndi = "com.example.contracts.distributed.services.ConverterBeanRemote";
			jndi = "java:global/demos-app/demos-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterBeanRemote";
			ConverterBeanRemote conv = (ConverterBeanRemote) ctx.lookup(jndi);
			System.out.println("Cambio: " + conv.yenToEuro(new BigDecimal(10)));

//			System.out.println("Peticion ConverterBeanLocal"); 
//			jndi = "java:global/demos-app/demos-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterBeanLocal";
//			ConverterBeanLocal convL = (ConverterBeanLocal) ctx.lookup(jndi);
//			System.out.println("Cambio: " + convL.dollarToYenLocal(new BigDecimal(10)));

			System.out.println("Peticiones LikesBeanRemote");
			LikesBeanRemote like = (LikesBeanRemote) ctx
					.lookup("com.example.contracts.distributed.services.LikesBeanRemote");
			for (int i = 0; i < 10; i++) {
				System.out.println("Like (stateful): You has send " + like.getHits() + " like(s).");
			}

			System.out.println("Peticion CounterBean");
			CounterBean cont = (CounterBean) ctx.lookup(
					"java:global/demos-app/demos-ejb/CounterBean!com.example.presentation.services.enterprise.CounterBean");
			System.out.println("Cont: " + cont.getHits());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static InitialContext getInitialContext() throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
		props.put(Context.PROVIDER_URL, "iiop://localhost:3700");
		return new InitialContext(props);
	}

	private static ConnectionFactory getConnection(InitialContext ctx) throws NamingException {
		return (ConnectionFactory) getInitialContext().lookup("jms/cursoConnectionFactory");
	}

	private static JMSContext getJMSContext(ConnectionFactory conn) throws NamingException {
		return conn.createContext();
	}

	private static void mandaPedidos(String id) {
		InitialContext ctx;
		ConnectionFactory conn;
		try {
			ctx = getInitialContext();
			conn = getConnection(ctx);
			try (JMSContext context = getJMSContext(conn)) {
				Queue queue = (Queue) ctx.lookup("jms/peticionesQueue");
				JMSProducer producer = context.createProducer();
				for (int i = 0; i < 10; i++) {
					String body = "Pedido de las " + LocalDateTime.now() + " de " + id;
					producer.send(queue, body);
					System.out.println("Pedido enviado por " + id + ": " + body);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void procesaPedidos(String id) {
		InitialContext ctx;
		ConnectionFactory conn;
		Queue facturas = null;
		try {
			ctx = getInitialContext();
			conn = getConnection(ctx);
			try (JMSContext context = getJMSContext(conn)) {
				Queue pedios = (Queue) ctx.lookup("jms/peticionesQueue");
				JMSConsumer consumer = context.createConsumer(pedios);
				while (true) {
					String body = consumer.receiveBody(String.class, 30000);
					if (body == null)
						break;
					Thread.sleep(1000);
					body = "Factura de " + id + " para el " + body.toLowerCase();
					if (facturas == null)
						facturas = (Queue) ctx.lookup("jms/respuestasQueue");
					context.createProducer().send(facturas, body);
					System.out.println("Factura enviada por " + id + ": " + body);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Connection connectionListener = null;

	private static void recibeFacturas(String id) {
		InitialContext ctx;
		ConnectionFactory conn;
		try {
			ctx = getInitialContext();
			conn = getConnection(ctx);
			connectionListener = conn.createConnection();
			Session session = connectionListener.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue facturas = (Queue) ctx.lookup("jms/respuestasQueue");
			MessageConsumer consumerListener = session.createConsumer(facturas);
			consumerListener.setMessageListener(message -> {
				try {
					System.out.println("Factura recibida por " + id + ": " + ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			connectionListener.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void publicador(String nombre) {
		List<Integer> peloton = new ArrayList<Integer>();
		for (int i = 1; i <= 10; peloton.add(i++))
			;
		Collections.shuffle(peloton);
		Random rnd = new Random();
		InitialContext ctx;
		ConnectionFactory conn;
		try {
			ctx = getInitialContext();
			conn = getConnection(ctx);
			try (JMSContext context = getJMSContext(conn)) {
				Topic topic = (Topic) ctx.lookup("jms/sensoresTopic");
				JMSProducer producer = context.createProducer();
				for (int dorsal : peloton) {
					String body = "SEN" + nombre + ":" + dorsal;
					producer.send(topic, body);
					System.out.println(body);
					Thread.sleep(rnd.nextInt(5) * 500);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void suscriptor(String id) {
		InitialContext ctx;
		ConnectionFactory conn;
		try {
			ctx = getInitialContext();
			conn = getConnection(ctx);
			Topic topic = (Topic) ctx.lookup("jms/sensoresTopic");
			Connection connection = conn.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumerListener = session.createConsumer(topic);
			consumerListener.setMessageListener(message -> {
				try {
					System.out.println("Evento " + message.getJMSMessageID() + " procesado por " + id + ": "
							+ ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			connection.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
