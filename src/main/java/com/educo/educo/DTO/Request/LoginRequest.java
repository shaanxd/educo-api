package com.educo.educo.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email cannot be empty.")
    private String email;
    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
