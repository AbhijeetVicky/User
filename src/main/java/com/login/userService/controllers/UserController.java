package com.login.userService.controllers;

import com.login.userService.dto.*;
import com.login.userService.dto.ResponseStatus;
import com.login.userService.exceptions.DuplicateEmailException;
import com.login.userService.exceptions.InvalidTokenException;
import com.login.userService.models.Token;
import com.login.userService.models.User;
import com.login.userService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = userService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);

        return responseDto;

    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto)  throws DuplicateEmailException {
                User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setUser(user);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return responseDto;
    }
    @PostMapping("/validate")
    public UserDto validateToken(@RequestHeader("Authorization") String token){

            User user = userService.validateToken(token);
            return UserDto.fromUser(user);

     }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
