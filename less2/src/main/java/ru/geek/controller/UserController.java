package ru.geek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geek.persist.entity.User;
import ru.geek.persist.repo.UserRepository;
import ru.geek.persist.repo.UserSpecification;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    //отобразить весь список пользователей
    @GetMapping //обрабатываем запрос типа GET
    public String allUsers(Model model,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "email", required = false) String email) {
        logger.info("Filtering by name: {} email: {}", name, email);

        Specification<User> spec = UserSpecification.trueLiteral();

        if(name !=null && name.isEmpty()){
            spec.and(UserSpecification.loginLike(name));
        }
        if(email !=null && email.isEmpty()){
            spec.and(UserSpecification.emailLike(email));
        }

        List<User> users = userRepository.findAll(spec);

        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model){
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "user_delete";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user"; //остаемся на той же странице
        }

//        bindingResult.rejectValue(user.getPassword(),user.getMatchingPassword());
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@Valid User user) {
        userRepository.delete(user);
        return "redirect:/user";
    }


}
