package com.crackbyte.controller;


import com.crackbyte.Employee;
import com.crackbyte.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping(value = "/demo/test", produces = "application/json", consumes = "application/json")
public class DemoController {

    @Autowired
    private Employee employee; // This is a prototype bean and will be different for each request

//    @PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> get() {
        employee.sayHello();
        System.out.println("Employee HashCode:" + employee.hashCode());
        employee.setName("demo");
        Map<String, Object> pong = new HashMap<>();
        pong.put("hash", employee.hashCode());
        pong.put("message", "Hello DEMO GET!");
        pong.put("name", employee.getName());
        return pong;
    }


    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> post(@RequestBody EmployeeDTO dto) {
        employee.sayHello();
        System.out.println("Employee HashCode:" + employee.hashCode());
        dto.setHasCode(employee.hashCode());
        dto.setId(UUID.randomUUID().toString());
        return ResponseEntity.ok(dto);
    }

}
