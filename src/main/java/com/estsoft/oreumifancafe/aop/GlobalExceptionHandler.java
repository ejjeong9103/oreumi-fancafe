package com.estsoft.oreumifancafe.aop;

import com.estsoft.oreumifancafe.exceptions.ForbiddenException;
import com.estsoft.oreumifancafe.exceptions.UnauthorizedException;
import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class, ForbiddenException.class, UserNotFoundException.class})
    public String handleUnauthorizedException(UnauthorizedException ex,
                                              Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errorPage"; // 에러 페이지로 리다이렉트
    }
}
