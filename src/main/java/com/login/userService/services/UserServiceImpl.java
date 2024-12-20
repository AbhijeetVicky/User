package com.login.userService.services;

import com.login.userService.models.Token;
import com.login.userService.models.User;
import com.login.userService.repositories.TokenRepository;
import com.login.userService.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            TokenRepository tokenRepository
    ){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.tokenRepository=tokenRepository;
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional  = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            // throw exception or redirect user to sign up
        }

        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            //throw anexception
            return null;
        }

        Token token = createToken(user);
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    @Override
    public User signUp(String name, String email, String password) {
            Optional<User> userOptinal = userRepository.findByEmail(email);
            if(userOptinal.isPresent()){
                //Throws an exception or redirect user to login
            }
            User user  = new User();
            user.setName(name);
            user.setEmail(email);
            user.setHashedPassword(bCryptPasswordEncoder.encode(password));


            userRepository.save(user);
            return user;

    }

    @Override
    public User validateToken(String token) {
        Optional<Token> optionalToken = tokenRepository
                .findByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());


        if(optionalToken.isEmpty()){
            //throw an exception or invalid null
            return null;
        }

         return optionalToken.get().getUser();
    }

    @Override
    public void logout(String token) {
        //H.W
    }

    public Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        //RandomStringUtils.randomAlphanumeric(120) - these are came from Apache Commons lang
        token.setValue( RandomStringUtils.randomAlphanumeric(120));

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR,30);
        Date  getDate30DaysFromToday = calendar.getTime();

        token.setExpiryAt(getDate30DaysFromToday);
        token.setDeleted(false);

        return token;
    }
}
