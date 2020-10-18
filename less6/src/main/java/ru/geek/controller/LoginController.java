package ru.geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geek.persist.entity.Role;
import ru.geek.persist.entity.User;
import ru.geek.persist.repo.UserRepository;

@Controller
@RequestMapping("/my-login")
public class LoginController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String login(){
        return "mylogin";
    }

}

