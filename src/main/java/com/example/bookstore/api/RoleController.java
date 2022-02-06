package com.example.bookstore.api;

import com.example.bookstore.domain.dto.RoleDTO;
import com.example.bookstore.domain.model.Role;
import com.example.bookstore.domain.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "Get all roles")
    @GetMapping
    public ResponseEntity<List<Role>> findRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get role by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> getRoleById(@Valid @PathVariable final Long id) {
        return new ResponseEntity<>(roleService.findRole(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Add new role")
    @PostMapping
    public ResponseEntity<Role> addRole(@Valid @RequestBody final RoleDTO roleDTO) throws URISyntaxException {
        Role result = roleService.addRole(roleDTO);
        return ResponseEntity.created(new URI("/roles/" + result.getId())).body(result);
    }

    @ApiOperation(value = "Update role")
    @PutMapping
    public ResponseEntity<String> updateRole(@Valid @RequestBody final RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);
        return ResponseEntity.ok().body("Role successfully updated");
    }

    @ApiOperation(value = "Delete role")
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteRole(@PathVariable String name) {
        roleService.deleteRole(name);
        return new ResponseEntity<>("Role successfully deleted", HttpStatus.OK);
    }
}
