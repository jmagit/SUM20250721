package com.laboratorio.ejb;

import jakarta.ejb.Remote;

@Remote
public interface HelloRemote {
    String sayHello(String name);
}
