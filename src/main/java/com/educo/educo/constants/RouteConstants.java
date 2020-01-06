package com.educo.educo.constants;

public class RouteConstants {
    public static final String AUTH_ALL = "/api/auth/**";

    public static final String AUTH_ROOT = "/api/auth";
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";

    public static final String ADMIN_ROOT = "/api/admin";
    public static final String ADMIN_GET_USERS = "/users";

    public static final String COMMENT_ROOT = "/api/comment";
    public static final String COMMENT_ADD_COMMENT = "/create-comment";

    public static final String COMMENT_UPVOTE = "/upvote/{id}";
    public static final String COMMENT_DOWNVOTE = "/downvote/{id}";

    public static final String QUESTION_ROOT = "/api/question";
    public static final String QUESTION_ADD_QUESTION = "/create-question";
    public static final String QUESTION_GET_QUESTION = "/{id}";
    public static final String QUESTION_GET_QUESTIONS = "/";

}
