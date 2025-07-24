package com.laboratorio.springclient.controller;

import com.laboratorio.springclient.service.EjbClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private EjbClientService ejbClientService;

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return ejbClientService.getHello(name);
    }
}
