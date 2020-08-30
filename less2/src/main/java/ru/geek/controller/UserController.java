package ru.geek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geek.persistance.User;
import ru.geek.persistance.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //отобразить весь список пользователей
    @GetMapping //обрабатываем запрос типа GET
    public String allUsers(Model model) throws SQLException {
        List<User> allUsers = userRepository.getAllUsers();
        model.addAttribute("users",allUsers);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws SQLException {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "user_update";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) throws SQLException {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "user_delete";
    }

    @GetMapping("/create/{id}")
    public String createUser(@PathVariable("id") Long id, Model model) throws SQLException {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "user_create";
    }

    @PostMapping("/update")
    public String updateUser(User user) throws SQLException {
        userRepository.update(user);
        return "redirect:/user";
    }

    @PostMapping("/create")
    public String insertUser(User user) throws SQLException {
        userRepository.insert(user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteUser(User user) throws SQLException {
        userRepository.delete(user);
        return "redirect:/user";
    }


}
