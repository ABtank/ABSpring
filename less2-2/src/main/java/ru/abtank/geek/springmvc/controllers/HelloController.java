package ru.abtank.geek.springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// contextPath  http://localhost:8189/app
@Controller
public class HelloController {

    // http://localhost:8189/app ->/hello
    @RequestMapping(value = "/hello" , method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(){
        return "Hello";
    }

    // http://localhost:8189/app ->/hello2
    @GetMapping("/hello2")
    @ResponseBody
    public String sayHello2(){
        return "<html><body><h2>Hello2</h2></body></html>";
    }

    // http://localhost:8189/app ->/hello2
    @PostMapping("/hello2")
    @ResponseBody
    public String sayHello3(){
        return "Hello2";
    }

    // http://localhost:8189/app ->/hello_page
    @GetMapping("/hello_page")
    public String sayHelloPage(){
        return "hello";
    }
}
