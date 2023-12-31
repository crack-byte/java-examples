package com.crackbyte.controller;


import com.crackbyte.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello/test")
public class HelloController {
    @Autowired
    private Employee employee;

    @RequestMapping( method = RequestMethod.GET)
    public Map<String, Object> get() {
        employee.sayHello();
        System.out.println("Employee HashCode:" + employee.hashCode());
        Map<String, Object> pong = new HashMap<>();
        pong.put("hash", employee.hashCode());
        pong.put("message", "Hello GET!");
        pong.put("name", employee.getName());
        return pong;
    }

    @RequestMapping( method = RequestMethod.POST)
    public Map<String, Object> post(@RequestBody Map<String, Object> body) {
        employee.sayHello();
        System.out.println("Employee HashCode:" + employee.hashCode());
        Map<String, Object> pong = new HashMap<>();
        pong.put("hash", employee.hashCode());
        pong.put("message", "Hello GET!");
        pong.put("name", employee.getName());
        pong.putAll(body);
        return pong;
    }
}
