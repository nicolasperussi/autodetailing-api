package com.nicolasperussi.autodetailing_api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nicolasperussi.autodetailing_api.domain.User;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.LoginDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.LoginResponseDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.RegisterUserDTO;
import com.nicolasperussi.autodetailing_api.exceptions.DatabaseException;
import com.nicolasperussi.autodetailing_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public LoginResponseDTO login(LoginDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                data.email(),
                data.password()
        );

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String token = this.generateToken(user);

        return new LoginResponseDTO(token, user);
    }

    public String register(RegisterUserDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            throw new DatabaseException("This e-mail is already in use.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        RegisterUserDTO newUser = new RegisterUserDTO(data.name(), data.email(), encryptedPassword, data.role());

        String userId = this.userService.create(newUser).getId();

        return userId;

    }

    public String generateToken(User user) {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("autodetailing-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(alg);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);

            return JWT.require(alg)
                    .withIssuer("autodetailing-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    public Instant genExpirationDate() {
        return LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
