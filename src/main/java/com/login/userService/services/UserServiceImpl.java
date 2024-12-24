package com.login.userService.services;

import com.login.userService.exceptions.DuplicateEmailException;
import com.login.userService.exceptions.InvalidCredentialsException;
import com.login.userService.exceptions.InvalidTokenException;
import com.login.userService.exceptions.UserNotFoundException;
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
        // 1. Case: User not found
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " not found.");
        }

        User user = userOptional.get();
        // 2. Case: Invalid credentials (password does not match)
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            throw new InvalidCredentialsException("Invalid password for user ");
        }

        Token token = createToken(user);
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    @Override
    public User signUp(String name, String email, String password) throws DuplicateEmailException {
            Optional<User> userOptinal = userRepository.findByEmail(email);

            if(userOptinal.isPresent()){
                throw new DuplicateEmailException("Email is already registered.");
            }
            User user  = new User();
            user.setName(name);
            user.setEmail(email);
            user.setHashedPassword(bCryptPasswordEncoder.encode(password));


            userRepository.save(user);
            return user;

    }

    @Override
    public User validateToken(String token) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository
                .findByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());

        // 1. Case: Token is deleted
        if (optionalToken.isPresent() && optionalToken.get().getDeleted()) {
            throw new InvalidTokenException("Token is deleted. Please log in again.");
        }

        // 2. Case: Token not found or invalid
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Invalid token.");
        }

        // 3. Case: Token has expired
        Token tokenEntity = optionalToken.get();
        if (tokenEntity.getExpiryAt().before(new Date())) {
            throw new InvalidTokenException("Token has expired.");
        }


        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String tokenValue) {
        //H.W
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);

        if (optionalToken.isEmpty()) {
            //Throw some exception
        }

        Token token = optionalToken.get();

        token.setDeleted(true);
        tokenRepository.save(token);
    }

    public Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        //RandomStringUtils.randomAlphanumeric(120) - these are came from Apache Commons lang
        token.setValue( RandomStringUtils.randomAlphanumeric(120)); // Read about UUID

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
