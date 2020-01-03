package com.educo.educo.security;

public class SecurityConstants {
    static final String AUTH_URLS = "/api/users/**";
    static final String JWT_SECRET = "JWT_SECRET_KEY";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long VALID_DURATION = 3600000;
}
