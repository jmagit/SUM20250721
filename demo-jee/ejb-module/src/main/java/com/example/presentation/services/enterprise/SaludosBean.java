package com.example.presentation.services.enterprise;

import com.example.contracts.distributed.services.SaludosRemote;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless; // O @Stateful, @Singleton

@Stateless // Indica que es un EJB sin estado
@LocalBean
public class SaludosBean implements SaludosRemote {

    @Override
    public String saludar(String nombre) {
        return "¡Hola, " + nombre + " desde el EJB de Jakarta EE!";
    }
}
