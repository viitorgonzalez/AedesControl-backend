package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.LoginRequest;
import com.aedescontrol.backend.model.User;
import com.aedescontrol.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var userOPT = userRepository.findByEmail(loginRequest.email());
        User user = userOPT.orElseThrow(() -> new BadCredentialsException("email or password is invalid!"));

        if (!user.isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("email or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L; // 5 min

        var claims = JwtClaimsSet.builder()
                .issuer("AedesControlBackend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        var cookie = new jakarta.servlet.http.Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int) expiresIn);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        var cookie = new jakarta.servlet.http.Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
