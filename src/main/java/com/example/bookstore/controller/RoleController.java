package com.example.bookstore.controller;

import com.example.bookstore.dto.RoleDTO;
import com.example.bookstore.exception.RoleFoundException;
import com.example.bookstore.exception.RoleNotFoundException;
import com.example.bookstore.model.Role;
import com.example.bookstore.service.impl.RoleServiceImpl;
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
@RequestMapping("/bookstore/v1/roles")
public class RoleController {

    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "Get all roles")
    @GetMapping(value = "/")
    public ResponseEntity<List<Role>> findRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get role by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> getRoleById(@Valid @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(roleService.findRole(id), HttpStatus.OK);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Add new role")
    @PostMapping(value = "/add")
    public ResponseEntity<Role> addRole(@Valid @RequestBody final RoleDTO roleDTO) {
        try {
            Role result = roleService.addRole(roleDTO);
            return ResponseEntity.created(new URI("/bookstore/v1/roles/" + result.getId())).body(result);
        } catch (RoleFoundException | URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Update role")
    @PutMapping(value = "/update")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody final RoleDTO roleDTO) {
        try {
            Role result = roleService.updateRole(roleDTO);
            return ResponseEntity.ok().body(result);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete role")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
