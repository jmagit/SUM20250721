package com.example;

import java.math.BigDecimal;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.contracts.distributed.services.ConverterBeanLocal;
import com.example.contracts.distributed.services.ConverterBeanRemote;
import com.example.contracts.distributed.services.SaludosRemote;
import com.example.presentation.services.enterprise.ConverterBean;
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
    @EJB
    private LikesBeanPropio ejb;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }

	@GET
	@Path("propio")
	@Produces(MediaType.TEXT_PLAIN)
	public Response propio() {
		return Response.ok(ejb.getHits()).build();
	}


    @Inject
    private SaludosBean saludo;
//    @EJB(lookup = "java:global/ear-module/ejb-module/SaludosBean")
    private SaludosRemote saludo1;
    @EJB(lookup = "java:global/ear-module/ejb-module/SaludosBean!com.example.contracts.distributed.services.SaludosRemote")
    private SaludosRemote saludo2;
    @EJB(name = "com.example.contracts.distributed.services.SaludosRemote")
    private SaludosBean saludo3;
    
	@EJB
	private ConverterBean converter;
	@EJB(lookup = "java:global/ear-module/ejb-module/ConverterBean!com.example.contracts.distributed.services.ConverterBeanLocal")
	private ConverterBeanLocal converter1;
	@EJB(lookup = "java:global/ear-module/ejb-module/ConverterBean!com.example.contracts.distributed.services.ConverterBeanRemote")
	private ConverterBeanRemote converter2;
	@EJB(lookup = "java:global/ear-module/ejb-module/ConverterBean!com.example.presentation.services.enterprise.ConverterBean")
	private ConverterBean converter3;
//	@EJB(name = "com.example.contracts.distributed.services.ConverterBeanRemote#com.example.contracts.distributed.services.ConverterBeanRemote")
//	private ConverterBeanRemote converter;
    
	@GET
	@Path("cambio")
	public Response cambio() {
		StringBuilder sb = new StringBuilder();
		if(saludo != null)
			sb.append("saludo");
		if(saludo1 != null)
			sb.append("saludo1");
		if(saludo2 != null)
			sb.append("saludo2");
		if(saludo3 != null)
			sb.append("saludo3");
		if(converter != null)
			sb.append("converter\n");
		if(converter1 != null)
			sb.append("converter1\n");
		if(converter2 != null)
			sb.append("converter2\n");
		if(converter3 != null)
			sb.append("converter3\n");
		if(sb.length() == 0) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Saludos EJB no esta disponible").build();
		}
		return Response.ok(sb.toString()).build();
//		return Response.ok(converter.dollarToYen(BigDecimal.ONE)).build();
	}
	
	
	@GET
	@Path("cotilla")
	@Produces(MediaType.TEXT_PLAIN)
	public Response cotilla() {
		StringBuilder sb = new StringBuilder();
		try {
			InitialContext ctx = new InitialContext();
			sb.append("NameInNamespace: %s\n".formatted(ctx.getNameInNamespace()));
			sb.append("Environment: " + ctx.getNameInNamespace() + "\n");
			ctx.getEnvironment().forEach((k, v) -> sb.append(k + ": " + v + "\n"));
//			ctx.
		} catch (NamingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(sb.toString()).build();
//		return Response.ok(converter.dollarToYen(BigDecimal.ONE)).build();
	}
}
