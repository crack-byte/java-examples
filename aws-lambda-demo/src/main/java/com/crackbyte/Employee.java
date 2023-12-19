package com.crackbyte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Employee {
    private static final Logger log = LoggerFactory.getLogger(Employee.class);
    private String name;

    public void sayHello() {
        log.info("HashCode:{}", this);
        System.out.println("Hello World");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
