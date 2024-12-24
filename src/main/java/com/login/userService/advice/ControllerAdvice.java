package com.login.userService.advice;

import com.login.userService.dto.ErrorDto;
import com.login.userService.exceptions.DuplicateEmailException;
import com.login.userService.exceptions.InvalidCredentialsException;
import com.login.userService.exceptions.InvalidTokenException;
import com.login.userService.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DuplicateEmailException.class)
        public ResponseEntity<ErrorDto> handleDuplicateEmailException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Email is already registered.");

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Invalid or expired token.");

        return new ResponseEntity<>(errorDto,HttpStatus.BAD_REQUEST);
    }

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
//        ErrorDto error = new ErrorDto();
//        error.setMessage("User with not found.");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    // Handle InvalidCredentialsException
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }


}
