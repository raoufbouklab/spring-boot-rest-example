package com.example.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
