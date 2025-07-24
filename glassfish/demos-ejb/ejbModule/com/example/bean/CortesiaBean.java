package com.example.bean;

import com.example.ejb.CortesiaRemote;

import jakarta.ejb.Stateless;

@Stateless
public class CortesiaBean implements CortesiaRemote {
	
	@Override
	public String saludar(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Hola, " + nombre + "!";
	}
	
	@Override
	public String despedir(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Adiós, " + nombre + "!";
	}
}
