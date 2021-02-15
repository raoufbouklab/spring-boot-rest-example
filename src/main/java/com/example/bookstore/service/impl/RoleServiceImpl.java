package com.example.bookstore.service.impl;

import com.example.bookstore.dto.RoleDTO;
import com.example.bookstore.exception.RoleFoundException;
import com.example.bookstore.exception.RoleNotFoundException;
import com.example.bookstore.model.Role;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_EXIST = "Role exist";
    private static final String ROLE_NOT_FOUND = "Role not found";
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
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
            throw new RoleNotFoundException(ROLE_NOT_FOUND);
        }
    }

    public Role addRole(RoleDTO roleDTO) {
        logger.info("Add role : {}", roleDTO.getName());
        Optional<Role> role = roleRepository.findRoleByName(roleDTO.getName());
        if (role.isPresent()) {
            throw new RoleFoundException(ROLE_EXIST);
        }
        return roleRepository.save(new Role(roleDTO.getName()));
    }

    public Role updateRole(RoleDTO roleDTO) {
        logger.info("Update role : {}", roleDTO.getName());
        Optional<Role> role = roleRepository.findRoleByName(roleDTO.getName());
        if (role.isPresent()) {
            return roleRepository.save(new Role(roleDTO.getName()));
        } else {
            throw new RoleNotFoundException(ROLE_NOT_FOUND);
        }
    }

    public void deleteRole(String name) {
        logger.info("Delete role : {}", name);
        Optional<Role> role = roleRepository.findRoleByName(name);
        if (role.isPresent()) {
            roleRepository.deleteByName(name);
        } else {
            throw new RoleNotFoundException(ROLE_NOT_FOUND);
        }
    }
}
