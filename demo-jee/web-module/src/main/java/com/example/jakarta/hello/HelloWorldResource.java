package com.example.jakarta.hello;

import java.math.BigDecimal;

import com.example.contracts.distributed.services.ConverterBeanLocal;
import com.example.contracts.distributed.services.ConverterBeanRemote;
import com.example.contracts.distributed.services.SaludosRemote;
import com.example.presentation.services.enterprise.SaludosBean;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
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
	
////	@EJB(lookup = "java:global/demo-jee-ear/demo-jee-ejb/ConverterBean!com.example.contracts.distributed.services.ConverterBeanRemote")
//	@EJB(name = "com.example.contracts.distributed.services.ConverterBeanRemote#com.example.contracts.distributed.services.ConverterBeanRemote")
//	private ConverterBeanRemote converter;
	@EJB
	private ConverterBeanLocal converter;

    @EJB(lookup = "java:global/ear-module/ejb-module/SaludosBean!com.example.contracts.distributed.services.SaludosRemote")
    private SaludosRemote saludo1;
    @EJB(name = "com.example.contracts.distributed.services.SaludosRemote")
    private SaludosRemote saludo2;
    @Inject
    private SaludosBean saludo3;
    
	@GET
	@Path("cambio")
	public Response cambio() {
		StringBuilder sb = new StringBuilder();
		if(saludo1 != null)
			sb.append("saludo1");
		if(saludo2 != null)
			sb.append("saludo2");
		if(saludo3 != null)
			sb.append("saludo3");
		if(converter != null)
			sb.append("converter");
		if(sb.length() == 0) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Saludos EJB no esta disponible").build();
		}
		return Response.ok(sb.toString()).build();
//		return Response.ok(converter.dollarToYen(BigDecimal.ONE)).build();
	}
}
