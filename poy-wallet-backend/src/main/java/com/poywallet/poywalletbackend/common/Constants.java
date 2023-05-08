package com.poywallet.poywalletbackend.common;

public class Constants {
    public static final String BASE_URL_FE = "http://localhost:3000/";

    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static final String SUCCESS = "Success";

    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String UNAUTHORIZED_ERROR = "Unauthorized error: {}";
    public static final String NOT_FOUND_USERNAME = "User with username of {0} is not found";

    public static final String INVALID_JWT_SIGN = "Invalid JWT signature: {}";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    public static final String JWT_EXPIRED = "JWT token is expired: {}";
    public static final String JWT_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_EMPTY = "JWT claims string is empty: {}";
    public static final String CANNOT_SET_AUTH = "Cannot set user authentication: {}";

    public static final String ALREADY_EXISTS_USER_NAME = "User with the same username already exists";
    public static final String ALREADY_EXISTS_USER_EMAIL = "User with the same email already exists";

    // CONSTANT LOGGING
    public static final String CREATED_USER = "User is created (username: {})";
    public static final String LOGGED_IN_USER = "User logged in (username: {})";
}
