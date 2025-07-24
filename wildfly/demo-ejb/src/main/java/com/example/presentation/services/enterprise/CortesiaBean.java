package com.example.presentation.services.enterprise;

import com.example.contracts.distributed.services.CortesiaRemote;

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
