package com.example.presentation.services.rest;

import com.example.contracts.distributed.services.CalculadoraLocal;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/calculadora")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CalculadoraResource {
	private CalculadoraLocal calc;
	
	@Inject
	public CalculadoraResource(CalculadoraLocal calc) {
		this.calc = calc;
	}
	
	@GET
	@Path("suma")
	public Response suma(@QueryParam(value = "op1") int a, @QueryParam(value = "op1") int b) {
		return Response.ok(calc.suma(a, b)).build();
	}
}
