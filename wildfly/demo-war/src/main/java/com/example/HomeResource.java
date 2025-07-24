package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.contracts.distributed.services.ConverterRemote;
import com.example.contracts.distributed.services.CortesiaRemote;
import com.example.contracts.distributed.services.CounterRemote;

import jakarta.annotation.Resource;

@RestController
public class HomeResource {

	@GetMapping("/saludo")
	public String index() {
		return "Welcome to the Spring JEE Application!";
	}

	@GetMapping("/ejb")
	public String ejb() {
		try {
			var obj = new JndiTemplate().lookup("java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote");
			return obj == null ? "EJB not found" : "EJB found: " + obj.getClass().getName();
		} catch (NamingException e) {
			return "Error: " + e.getMessage();
		}
	}

//	@Resource(name = "java:comp/env")
//	Context context;

	@GetMapping(path = { "/cotilla", "demos" })
	public List<String> demos() throws NamingException {
//        Hashtable<String, String> jndiProps = new Hashtable<>();
//        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
//        Context context = new InitialContext(jndiProps);
		Context context = new JndiTemplate().getContext();

		var result = new ArrayList<String>();
		var list = context.list("java:global");
		while (list.hasMore()) {
			NameClassPair pair = list.next();
			result.add("java:global/" + pair.getName() + " --> " + pair.getClassName());
		}
		list = context.list("java:comp/env");
		while (list.hasMore()) {
			NameClassPair pair = list.next();
			result.add("java:comp/env/" + pair.getName() + " --> " + pair.getClassName());
		}
		return result;
	}

//	@Resource(lookup = "java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote")
	@Autowired
	CortesiaRemote cortesiaRemote;

	@GetMapping("/hello/{name}")
	String hello(@PathVariable String name) throws NamingException {
//    	CortesiaRemote cortesiaRemote = (CortesiaRemote) new JndiTemplate().lookup("java:global/demo-ejb/CortesiaBean!com.example.ejb.CortesiaRemote");
		return cortesiaRemote.saludar(name);
	}

	@Autowired
	ConverterRemote converterRemote;

	@GetMapping("/yenes/{value}")
	BigDecimal yenes(@PathVariable Double value) throws NamingException {
		return converterRemote.dollarToYen(new BigDecimal(value));
	}

	@GetMapping("/euros/{value}")
	BigDecimal dolares(@PathVariable Double value) throws NamingException {
		return converterRemote.yenToEuro(new BigDecimal(value));
	}

	@Autowired
	CounterRemote counterRemote;

	@GetMapping("/counter")
	public String counter() {
		return "Contador: " + counterRemote.getHits();
	}
}