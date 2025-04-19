package com.wtc.springbootquickstart;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String name) {
        System.out.println("name: " + name);
        return "Hello " + name + "~";
    }
    
}
