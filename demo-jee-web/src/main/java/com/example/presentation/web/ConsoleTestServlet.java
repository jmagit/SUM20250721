package com.example.presentation.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Enumeration;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.contracts.distributed.services.ConverterBeanLocal;
import com.example.contracts.distributed.services.Counter;
//import com.example.contracts.distributed.services.LikesBeanLocal;
//import com.example.contracts.distributed.services.SaludoBeanLocal;
//import com.example.contracts.distributed.services.SaludoBeanRemote;
//import com.example.contracts.domain.distributed.FacturasCommand;
//import com.example.contracts.domain.distributed.PedidosCommand;
//import com.example.contracts.domain.distributed.SensoresEvent;
//import com.example.infraestructure.events.SensoresTopic;
//import com.example.infraestructure.messages.FacturasQueue;
//import com.example.infraestructure.messages.PedidosQueue;
//import com.example.presentation.services.enterprise.CounterBean;
//import com.example.presentation.services.enterprise.SaludoBean;
//import com.example.presentation.web.ioc.Real;
//import com.example.presentation.web.ioc.Servicio;

@WebServlet("/pruebas")
public class ConsoleTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("/WEB-INF/parts/header.jsp").include(request, response);

//		cabeceras(request, response, out);
//		inyecciones(request, response, out);
//		conexion(request, response, out);
		ejb(request, response, out);
//		colas(request, response, out);
//		temas(request, response, out);

		request.getRequestDispatcher("/WEB-INF/parts/footer.jsp").include(request, response);
	}

//	@Inject @Real
//	private Servicio srv;
//	
//	private void inyecciones(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//		try {
//			out.println("<h2>" + srv.getNombre() + "</h2>");
//		} catch (Exception e) {
//			out.println("<h2>" + e.getClass().getCanonicalName() + ": " + e.getMessage() + "</h2>");
//		}
//	}
//	private void cabeceras(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//		out.println("<h2>cabeceras</h2>");
//		out.println("<ul>");
//		Enumeration<String> nombresDeCabeceras = request.getHeaderNames();
//		while (nombresDeCabeceras.hasMoreElements()) {
//			String cabecera = nombresDeCabeceras.nextElement();
//			out.println("<li><b>" + cabecera + ": </b>" + request.getHeader(cabecera) + "</li>");
//		}
//		out.println("</ul>");
//	}
//
//	@Resource(lookup = "SakilaDataSource")
//	private DataSource ds;
//
//	private void conexion(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//		out.println("<h2>DataSource</h2>");
//		out.println("<ul>");
//		out.println("<li>" + ds.getClass().getCanonicalName() + "</li>");
//		try (Connection con = ds.getConnection()) {
//			out.println("<li>" + con.getClass().getCanonicalName() + "</li>");
//			out.println("<li>" + con.getMetaData().getDatabaseProductName() + " v. "
//					+ con.getMetaData().getDatabaseProductVersion() + "</li>");
//			con.getClientInfo().forEach((k, v) -> out.println("<li><b>" + k + ": </b>" + v + "</li>"));
//		} catch (SQLException e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//		out.println("</ul>");
//	}

//	@EJB
//	private ConverterBeanLocal converter;
//	@EJB
//	private Counter counter;

	
	private void ejb(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		out.println("<h2>EJB</h2>");
		out.println("<ul>");
		
//		out.println("<li>Converter (stateless): " + converter.dollarToYen(BigDecimal.TEN) + " - "
//				+ converter.dollarToYenLocal(BigDecimal.TEN) + "</li>");
//		try {
//			int actual = counter.getHits();
//			out.println("<li>Counter (singleton): This page has been accessed " + actual + " time(s).</li>");
//			if (actual > 2) {
//				out.println("<li>Counter remove</li>");
//				counter.remove();
//			}
//		} catch (Exception e) {
//			out.println("<li>" + e.getClass().getCanonicalName() + ": " + e.getMessage() + "</li>");
//		}
//		out.println("<li><a href='" + request.getRequestURI() + "'>Abrir el enlace en una ventana de incognito</a></li>");
		out.println("</ul>");
	}

//	@Inject
//	PedidosQueue pedidosSender;
//	@Inject
//	FacturasQueue facturasReceiver;
//	
//	private void colas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//		out.println("<h2>EJB</h2>");
//		out.println("<ul>");
//		try {
//			out.println("<li>JMS Colas</li>");
//			String body = "Pedido de las " + LocalDateTime.now() + " de " + getClass().getSimpleName();
//			pedidosSender.send(body);
//			out.println("<li>PEDIDO: " + body + "</li>");
//			while (body != null) {
//				body = facturasReceiver.receive(1000);			
//				out.println("<li>FACTURA: " + (body == null ? "No se ha recibido la factura" : body) + "</li>");
//			}
//		} catch (Exception e) {
//			out.println("<li>" + e.getClass().getCanonicalName() + ": " + e.getMessage() + "</li>");
//		}
//		out.println("</ul>");
//	}
//	
//	@Inject
//	SensoresTopic sensorSender;
//	
//	private void temas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//		out.println("<h2>EJB</h2>");
//		out.println("<ul>");
//		try {
//			out.println("<li>JMS Temas</li>");
//			String body = "SRV1:" + LocalDateTime.now().getSecond();
//			sensorSender.send(body);
//			out.println("<li>Evento enviado: " + body + "</li>");
//		} catch (Exception e) {
//			out.println("<li>" + e.getClass().getCanonicalName() + ": " + e.getMessage() + "</li>");
//		}
//		out.println("</ul>");
//	}
//	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
////		doGet(request, response);
//	}

}
