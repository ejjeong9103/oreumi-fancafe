package com.estsoft.oreumifancafe.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String message) {
        super(message + "가 존재하지 않습니다.");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message + "가 존재하지 않습니다.", cause);
    }
}
