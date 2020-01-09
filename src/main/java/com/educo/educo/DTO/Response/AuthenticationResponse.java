package com.educo.educo.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String id;
    private String name;
    private String role;
    private long expirationInSeconds;
}
