package com.login.userService.security.services;


import com.login.userService.models.User;
import com.login.userService.repositories.UserRepository;
import com.login.userService.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDeatilsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDeatilsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    /*
     * We are simply returning user details object by fetching it from the DB
     * It is spring's responsibility to do the matching from the password entered from the browser
     * */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User Doesn't Exist");
        }

        User user = userOptional.get();
        //return user ; // can i do this ? -- No , we are returning the USER, but the method is returning the userDetails.
        return new CustomUserDetails(user);
    }
}
