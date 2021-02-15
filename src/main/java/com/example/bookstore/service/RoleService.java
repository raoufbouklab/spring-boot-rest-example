package com.example.bookstore.service;

import com.example.bookstore.dto.RoleDTO;
import com.example.bookstore.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findRole(Long id);

    Role addRole(RoleDTO roleDTO);

    void deleteRole(String name);

    Role updateRole(RoleDTO roleDTO);
}
