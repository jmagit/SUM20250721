package com.laboratorio.springclient.service;

import jakarta.naming.InitialContext;
import jakarta.naming.NamingException;
import com.laboratorio.ejb.HelloRemote;
import org.springframework.stereotype.Service;

@Service
public class EjbClientService {
    public String getHello(String name) {
        try {
            InitialContext ctx = new InitialContext();
            HelloRemote hello = (HelloRemote) ctx.lookup("java:global/ejb-server/HelloBean!com.laboratorio.ejb.HelloRemote");
            return hello.sayHello(name);
        } catch (NamingException e) {
            return "Error: " + e.getMessage();
        }
    }
}
