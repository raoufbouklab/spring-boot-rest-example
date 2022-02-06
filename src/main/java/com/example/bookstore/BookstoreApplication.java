package com.example.bookstore;

import com.example.bookstore.domain.model.Role;
import com.example.bookstore.domain.model.User;
import com.example.bookstore.domain.repository.RoleRepository;
import com.example.bookstore.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class BookstoreApplication {

    private final Logger logger = LoggerFactory.getLogger(BookstoreApplication.class);

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

        return args -> {
            var role1 = new com.example.bookstore.domain.model.Role("ADMIN");
            var role2 = new com.example.bookstore.domain.model.Role("USER");
            addRoleIfNotExist(roleRepository, role1);
            addRoleIfNotExist(roleRepository, role2);
            roleRepository.findAll().forEach(role -> logger.info(role.toString()));

            var roles = Set.of(role1, role2);
            var newUser = new User("raoufb", "Raouf", "Bouklab", "raouf.bouk@test.ca", "password", true, roles);

            addUserIfNotExist(userRepository, newUser);
            userRepository.findAll().forEach(user -> logger.info(user.toString()));
        };
    }

    private void addRoleIfNotExist(RoleRepository roleRepository, Role role) {
        var roleFound = roleRepository.findRoleByName(role.getName());
        if (roleFound.isEmpty()){
            roleRepository.save(role);
        }
    }

    private void addUserIfNotExist(UserRepository userRepository, User user) {
        var userFound = userRepository.findUserByUsername(user.getUsername());
        if (userFound.isEmpty()){
            userRepository.save(user);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

}
