/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.example.ejb;

import jakarta.ejb.Stateless;

/**
 * HelloServiceBean is a web service endpoint implemented as a stateless
 * session bean.
 */

@Stateless
public class HelloServiceBean {
    private final String message = "Hello, ";
    
    public HelloServiceBean() {}

    public String sayHello(String name) {
        return message + name + ".";
    }
}
