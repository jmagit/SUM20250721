package com.example.contracts.distributed.services;

import jakarta.ejb.Remote; // Para interfaz remota
//import jakarta.ejb.Local; // Para interfaz local

@Remote // O @Local si es para acceso interno al mismo EAR
public interface SaludosRemote {
 String saludar(String nombre);
}
