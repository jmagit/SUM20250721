package com.example.ejb;

import jakarta.ejb.Remote;

@Remote
public interface CortesiaRemote {

	String saludar(String nombre);

	String despedir(String nombre);

}