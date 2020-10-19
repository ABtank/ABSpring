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
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "Hello";
    }

    // http://localhost:8189/app ->/hello2
    @GetMapping("/hello2")
    @ResponseBody
    public String sayHello2() {
        return "<html><body><h2>Hello2</h2></body></html>";
    }

    // http://localhost:8189/app ->/hello2
    @PostMapping("/hello2")
    @ResponseBody
    public String sayHello3() {
        return "Hello2";
    }

    @GetMapping("/box_page")
    public String showBoxPage(Model model) {
        List<Box> boxes = boxService.getAllBoxes();
        model.addAttribute("boxes", boxes);
        return "hello";
    }

    // http://localhost:8189/app ->/hello_user?name1=Bob&surname=Jonson
    @GetMapping("/hello_user")
    @ResponseBody
    public String sayHelloUser(@RequestParam(name = "name1", defaultValue = "user") String username, @RequestParam(required = false) String surname) {
        return String.format("hello %s %s !!!", username, surname);
    }

    // http://localhost:8189/app ->/docs/100/info
    @GetMapping("/docs/{doc_id}/info")
    @ResponseBody
    public String showDocInfo(@PathVariable(name = "doc_id") Long id) {
        return "Document #" + id;
    }

    @PostMapping("/add_box")
    public String addNewBox(@RequestParam String color, @RequestParam int size) {
        Box box = new Box(1L, color, size);
        boxService.save(box);
        return "redirect:/box_page";
    }

    @GetMapping("/show_me_box")
    @ResponseBody
    public Box showMeBox(@ModelAttribute Box box) {
//        Box box = new Box(id, color,size);
        return box;
    }

    @PostMapping("/show_me_box")
    @ResponseBody
    public Box showJsonMeBox(@RequestBody Box box) {
//        Box box = new Box(id, color,size);
        box.setSize(box.getSize() + 25);
        return box;
    }

    @GetMapping("/delete_box/{id}")
    public String deleteBox(@PathVariable Long id) {
        boxService.deleteById(id);
        return "redirect:/box_page";
    }
}
