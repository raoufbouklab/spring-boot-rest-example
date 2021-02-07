package com.example.bookstore.service;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.dto.UserLoginDTO;
import com.example.bookstore.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUser(Long id);

    User register(UserDTO userDTO);

    User login(UserLoginDTO userLoginDTO);

    void deleteUser(Long id);

    User updateUser(UserDTO userDTO);
}
