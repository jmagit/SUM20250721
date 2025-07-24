package com.ejemplo.ejb;

import jakarta.ejb.Stateless;

@Stateless
public class HolaEJB {
    public String saludar(String nombre) {
        return "Hola desde EJB, " + nombre;
    }
}
