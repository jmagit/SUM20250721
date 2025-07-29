package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
public class SyncRmiClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncRmiClientApplication.class, args);
	}
	
    @Bean 
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/CortesiaService");
        rmiProxyFactory.setServiceInterface(CortesiaService.class);
        return rmiProxyFactory;
    }
    
    @Bean
    CommandLineRunner commandLineRunner(CortesiaService cortesiaService) {
		return args -> {
			System.out.println("Respuesta invocación remota: " + cortesiaService.saludar("Mundo"));
			System.out.println("Respuesta invocación remota: " + cortesiaService.despedir("mundo"));
		};
	}

}
