package com.example.bookstore.controller;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.dto.UserLoginDTO;
import com.example.bookstore.exception.UserFoundException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.model.User;
import com.example.bookstore.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bookstore/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(value = "/")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user by username/email and password")
    @GetMapping(value = "/login")
    public ResponseEntity<User> login(@Valid @RequestBody final UserLoginDTO userLoginDTO) {
        try {
            return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Add new user")
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@Valid @RequestBody final UserDTO userDTO) {
        try {
            User result = userService.register(userDTO);
            return ResponseEntity.created(new URI("/bookstore/v1/users/" + result.getId())).body(result);
        } catch (UserFoundException | URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Update user")
    @PutMapping(value = "/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody final UserDTO userDTO) {
        try {
            User result = userService.updateUser(userDTO);
            return ResponseEntity.ok().body(result);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
