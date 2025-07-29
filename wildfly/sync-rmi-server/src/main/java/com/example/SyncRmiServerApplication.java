package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@SpringBootApplication
public class SyncRmiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncRmiServerApplication.class, args);
	}

    @Bean 
    RmiServiceExporter exporter(CortesiaService implementation) {
        // Expose a service via RMI. Remote obect URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port
        Class<CortesiaService> serviceInterface = CortesiaService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

}
