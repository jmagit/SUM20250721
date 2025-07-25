package com.example.presentation.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.contracts.distributed.services.ConverterLocal;
import com.example.contracts.distributed.services.ConverterRemote;
import com.example.contracts.distributed.services.CortesiaRemote;
import com.example.contracts.distributed.services.CounterRemote;

import jakarta.ejb.EJB;

@RestController
public class HomeResource {

	@GetMapping("/saludo")
	public String index() {
		return "Welcome to the Spring JEE Application!";
	}

	@Autowired
	JndiTemplate jndiTemplate;

	record Info(String jndi, String className) {
	}

	@GetMapping("/jndi")
	public List<Info> jndi() {
		var result = new ArrayList<Info>();
		var list = List.of(
				"java:/ConnectionFactory",
				"java:/jms/queue/peticiones",
				"java:global/demo-ejb/PedidosQueue!com.example.contracts.domain.distributed.PedidosCommand",
				"java:global/demo-ejb/ConverterBean!com.example.presentation.services.enterprise.ConverterBean",
				"java:app/demo-ejb/ConverterBean!com.example.presentation.services.enterprise.ConverterBean",
				"java:module/ConverterBean!com.example.presentation.services.enterprise.ConverterBean",
				"java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterLocal",
				"java:app/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterLocal",
				"java:module/ConverterBean!com.example.contracts.distributed.services.ConverterLocal",
				"java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote",
				"java:app/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote",
				"java:module/ConverterBean!com.example.contracts.distributed.services.ConverterRemote",
				"java:jboss/exported/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote",
				"ejb:/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote",
				"java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote");
		list.forEach(name -> {
			try {
				var obj = jndiTemplate.lookup(name);
				result.add(new Info(name, obj == null ? "Not found" : obj.getClass().getName()));
			} catch (NamingException e) {
				result.add(new Info(name, "NamingException: " + e.getMessage()));
			}
		});
		return result;
	}

	@GetMapping(path = { "/listar" })
	public List<String> listar() throws NamingException {
//        Hashtable<String, String> jndiProps = new Hashtable<>();
//        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
//        Context context = new InitialContext(jndiProps);
		Context context = new InitialContext();

		var result = new ArrayList<String>();
		result.add("ENVIRONMENT");
		for (var env : context.getEnvironment().entrySet()) {
			result.add(env.getKey() + " = " + env.getValue());
		}
		for (var name : List.of("java:comp", "java:global", "java:app", "java:module", "ejb", "java:jboss", "java:/")) {
			result.add(name.toUpperCase());
			var list = context.list(name);
			while (list.hasMore()) {
				NameClassPair pair = list.next();
				result.add(name + (name.endsWith("/") ? "" : "/") + pair.getName() + " --> " + pair.getClassName());
			}
		}
		return result;
	}

//	@Resource(lookup = "java:global/demo-ejb/CortesiaBean!com.example.contracts.distributed.services.CortesiaRemote")
	@Autowired
	CortesiaRemote cortesiaRemote;

	@GetMapping("/hello/{name}")
	String hello(@PathVariable String name) throws NamingException {
//    	CortesiaRemote cortesiaRemote = (CortesiaRemote) new JndiTemplate().lookup("java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote");
		return cortesiaRemote.saludar(name);
	}

//	@Autowired
	@EJB(lookup = "ejb:/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterRemote")
	ConverterRemote converterRemote;

	@GetMapping("/yenes/{value}")
	BigDecimal yenes(@PathVariable Double value) throws NamingException {
		return converterRemote.dollarToYen(new BigDecimal(value));
	}

//	@Autowired
//	@EJB(lookup = "java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterLocal")
//	ConverterLocal converterLocal;

	@GetMapping("/euros/{value}")
	BigDecimal dolares(@PathVariable Double value) throws NamingException {
		return converterRemote.yenToEuro(new BigDecimal(value));
//		return converterLocal.yenToEuro(new BigDecimal(value));
//		var obj = jndiTemplate.lookup("java:global/demo-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterLocal");
//		return ((ConverterLocal)obj).yenToEuro(new BigDecimal(value));
	}

	@Autowired
	CounterRemote counterRemote;

	@GetMapping("/counter")
	public String counter() {
		return "Contador: " + counterRemote.getHits();
	}
}