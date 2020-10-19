package ru.abtank.geek.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.geek.springmvc.model.Box;
import ru.abtank.geek.springmvc.servises.BoxService;

import java.util.List;

// contextPath  http://localhost:8189/app
@Controller
public class HelloController {
    private BoxService boxService;

    @Autowired
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }

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

    @GetMapping("/box_page")
    public String showBoxPage(Model model){
        List<Box> boxes = boxService.getAllBoxes();
        model.addAttribute("boxes", boxes);
        return "hello";
    }

}
