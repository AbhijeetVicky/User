package com.login.userService.repositories;

import com.login.userService.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Token save(Token token);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(
            String tokenValue,
            Boolean deleted,
            Date date
    );


    Optional<Token> findByValueAndDeleted(String tokenValue, Boolean deleted);
}
