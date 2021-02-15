package com.example.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDTO {
    @NotBlank
    private String name;
}
