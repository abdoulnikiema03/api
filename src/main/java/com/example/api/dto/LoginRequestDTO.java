package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email n'est pas valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
}