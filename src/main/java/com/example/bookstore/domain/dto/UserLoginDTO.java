package com.example.bookstore.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
