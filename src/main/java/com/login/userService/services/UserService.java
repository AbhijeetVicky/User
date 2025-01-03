package com.login.userService.services;

import com.login.userService.exceptions.DuplicateEmailException;
import com.login.userService.models.Role;
import com.login.userService.models.Token;
import com.login.userService.models.User;

import java.util.List;

public interface UserService {
   public Token  login(String email, String password);

   public User signUp(String name, String email, String Password, List<Role> role) throws DuplicateEmailException;

   public User validateToken(String token);

   public void logout(String token);
}
