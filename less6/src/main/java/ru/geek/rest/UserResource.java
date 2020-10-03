package ru.geek.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geek.controller.NotFoundException;
import ru.geek.persist.entity.User;
import ru.geek.persist.repo.UserRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public User getUser(@PathVariable("id") int id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping (consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user){
        if(user.getId() != null){
            throw new IllegalArgumentException("id found in the create request");
        }
        userRepository.save(user);
        return user;
    }

    @PutMapping (consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user){
        if(user.getId() == null){
            throw new IllegalArgumentException("id found in the update request");
        }
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteUser(@PathVariable("id") int id){
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> IllegalArgumentExceptionHandler (IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlIntegrityConstraintViolationExceptionHandler (SQLIntegrityConstraintViolationException ex){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
