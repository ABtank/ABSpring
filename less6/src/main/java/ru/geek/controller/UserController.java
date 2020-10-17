package ru.geek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geek.persist.entity.User;
import ru.geek.persist.repo.UserRepository;
import ru.geek.persist.repo.UserSpecification;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //отобразить весь список пользователей
    @GetMapping //обрабатываем запрос типа GET
    public String allUsers(Model model,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        logger.info("\nFiltering by \nname: {} \nemail: {}\n", name, email);

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by("login").and(Sort.by("email")));

        Specification<User> spec = UserSpecification.trueLiteral();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(UserSpecification.loginLike(name));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.emailLike(email));
        }
        Page<User> usersPage = userRepository.findAll(spec, pageRequest);
        model.addAttribute("usersPage", usersPage);
        return "users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            NotFoundException exception = new NotFoundException();
            exception.setMessage(id.toString(),User.class.getSimpleName());
            return exception;
        });
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString(),User.class.getSimpleName()));
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@Valid User user) {
        userRepository.delete(user);
        return "redirect:/user";
    }

}
