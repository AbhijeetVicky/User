package com.login.userService.services;

import com.login.userService.models.Token;
import com.login.userService.models.User;

public interface UserService {
   public Token  login(String email, String password);

   public User signUp(String name, String email, String Password);

   public User validateToken(String token);

   public void logout(String token);
}