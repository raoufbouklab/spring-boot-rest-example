package com.example.bookstore.domain.service;

import com.example.bookstore.domain.dto.RoleDTO;
import com.example.bookstore.domain.exception.FoundException;
import com.example.bookstore.domain.exception.NotFoundException;
import com.example.bookstore.domain.model.Role;
import com.example.bookstore.domain.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private static final String ROLE_EXIST = "Role exist!";
    private static final String ROLE_NOT_FOUND = "Role not found";
    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        logger.info("Get all roles ");
        return roleRepository.findAll();
    }

    public Role findRole(Long id) {
        logger.info("Get role by id : {} ", id);
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
    }

    public Role addRole(RoleDTO roleDTO) {
        logger.info("Add role : {}", roleDTO.getName());
        Optional<Role> role = roleRepository.findRoleByName(roleDTO.getName());
        if (role.isPresent()) {
            throw new FoundException(ROLE_EXIST);
        }
        return roleRepository.save(new Role(roleDTO.getName()));
    }

    public void updateRole(RoleDTO roleDTO) {
        logger.info("Update role : {}", roleDTO.getName());
        Optional<Role> role = roleRepository.findRoleByName(roleDTO.getName());
        if (role.isPresent()) {
            roleRepository.save(new Role(roleDTO.getName()));
        } else {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
    }

    public void deleteRole(String name) {
        logger.info("Delete role : {}", name);
        Optional<Role> role = roleRepository.findRoleByName(name);
        if (role.isPresent()) {
            roleRepository.deleteByName(name);
        } else {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
    }
}
