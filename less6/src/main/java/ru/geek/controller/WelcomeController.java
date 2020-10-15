package ru.geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geek.persist.entity.User;
import ru.geek.persist.repo.UserRepository;

import java.security.Principal;
import java.util.List;

@RequestMapping("/")
@Controller
public class WelcomeController {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String welcomePage(){
        return "welcome";
    }
}
