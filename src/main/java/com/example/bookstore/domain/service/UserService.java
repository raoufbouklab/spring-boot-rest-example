package com.example.bookstore.domain.service;

import com.example.bookstore.domain.dto.UserDTO;
import com.example.bookstore.domain.dto.UserLoginDTO;
import com.example.bookstore.domain.exception.FoundException;
import com.example.bookstore.domain.exception.NotFoundException;
import com.example.bookstore.domain.model.Role;
import com.example.bookstore.domain.model.User;
import com.example.bookstore.domain.repository.RoleRepository;
import com.example.bookstore.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    public static final String USER_NOT_FOUND = "User not found";
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
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
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return user.get();
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
            throw new NotFoundException("Incorrect username or password");
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
            throw new FoundException("An other user found with the same username = " + userDTO.getUsername());
        }
    }

    public User updateUser(UserDTO userDTO) {
        logger.info("Update user: {} {}", userDTO.getFirstName(), userDTO.getLastName());
        Optional<User> user = userRepository.findUserByUsername(userDTO.getUsername());
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        user.get().setUsername(userDTO.getUsername());
        user.get().setFirstName(userDTO.getFirstName());
        user.get().setLastName(userDTO.getLastName());
        user.get().setEmail(userDTO.getEmail());
        user.get().setPassword(userDTO.getPassword());
        return userRepository.save(user.get());
    }

    public void deleteUser(Long id) {
        logger.info("Delete user: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        userRepository.delete(user.get());
    }
}
