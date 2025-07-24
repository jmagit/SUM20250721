package com.laboratorio.ejb;

import jakarta.ejb.Stateless;

@Stateless
public class HelloBean implements HelloRemote {
    @Override
    public String sayHello(String name) {
        return "Hola, " + name + "!";
    }
}
