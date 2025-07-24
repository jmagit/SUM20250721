package com.example.jakarta.hello;

import java.math.BigDecimal;

import com.example.contracts.distributed.services.ConverterBeanLocal;
import com.example.contracts.distributed.services.ConverterBeanRemote;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("hello")
public class HelloWorldResource {
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }
	
//	@EJB(lookup = "java:global/demo-jee-ear/demo-jee-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterBeanRemote")
	@EJB(name = "com.example.contracts.distributed.services.ConverterBeanRemote#com.example.contracts.distributed.services.ConverterBeanRemote")
	private ConverterBeanRemote converter;

	@GET
	@Path("cambio")
	public Response cambio() {
		return Response.ok(converter.dollarToYen(BigDecimal.ONE)).build();
	}
}
