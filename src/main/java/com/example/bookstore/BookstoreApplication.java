package com.example.bookstore;

import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
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
            var role1 = new Role((long) 1, "ADMIN");
            var role2 = new Role((long) 2, "USER");
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.findAll().forEach(role -> logger.info(role.toString()));

            var roles = Set.of(role1, role2);
            userRepository.save(new User((long) 1, "raoufb", "Raouf", "Bouklab", "raouf.bouklab@test.ca", "password", true, roles));
            userRepository.findAll().forEach(user -> logger.info(user.toString()));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

}
