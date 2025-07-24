package com.ejemplo.web;

import com.ejemplo.ejb.HolaEJB;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/saludo")
public class SaludoServlet extends HttpServlet {

    @EJB
    private HolaEJB holaEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String mensaje = holaEJB.saludar(nombre != null ? nombre : "mundo");
        resp.getWriter().write(mensaje);
    }
}
