package com.example.bookstore.service.impl;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.dto.UserLoginDTO;
import com.example.bookstore.exception.UserFoundException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND = "User not found";
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {
        logger.info("Get all users ");
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        logger.info("Get user by id : {} ", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    public User login(@Valid UserLoginDTO userLoginDTO) {
        logger.info("Get user by username and password");
        Optional<User> user = userRepository.findUserByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if (user.isPresent()) {
            return user.get();
        } else {
            user = userRepository.findUserByEmailAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            if (user.isPresent()) {
                return user.get();
            }
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    public User register(UserDTO userDTO) {
        logger.info("Add user: {}  {}", userDTO.getFirstName(), userDTO.getLastName());
        Optional<User> user = userRepository.findUserByUsername(userDTO.getUsername());
        if (user.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            Optional<Role> userRole = roleRepository.findRoleByName("USER");
            userRole.ifPresent(roles::add);
            var newUser = new User(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword(), true, roles);
            return userRepository.save(newUser);
        } else {
            throw new UserFoundException("An other user found with the same username = " + userDTO.getUsername());
        }
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        logger.info("Update user: {} {}", userDTO.getFirstName(), userDTO.getLastName());
        Optional<User> user = userRepository.findUserByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            user.get().setUsername(userDTO.getUsername());
            user.get().setFirstName(userDTO.getFirstName());
            user.get().setLastName(userDTO.getLastName());
            user.get().setEmail(userDTO.getEmail());
            user.get().setPassword(userDTO.getPassword());
            return userRepository.save(user.get());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    public void deleteUser(Long id) {
        logger.info("Delete user: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }
}
