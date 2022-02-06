package com.example.bookstore.api;

import com.example.bookstore.domain.dto.UserDTO;
import com.example.bookstore.domain.dto.UserLoginDTO;
import com.example.bookstore.domain.model.User;
import com.example.bookstore.domain.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable final Long id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by username/email and password")
    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@Valid @RequestBody final UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Add new user")
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@Valid @RequestBody final UserDTO userDTO) throws URISyntaxException {
        User result = userService.register(userDTO);
        return ResponseEntity.created(new URI("/users/" + result.getId())).body(result);
    }

    @ApiOperation(value = "Update user")
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody final UserDTO userDTO) {
        User result = userService.updateUser(userDTO);
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
