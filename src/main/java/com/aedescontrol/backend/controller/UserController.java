package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.LoginRequest;
import com.aedescontrol.backend.dto.UserDTO;
import com.aedescontrol.backend.model.User;
import com.aedescontrol.backend.repository.UserRepository;
import com.aedescontrol.backend.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("email ou senha inválida!"));

        if (!user.isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("email ou senha inválida!");
        }

        var now = Instant.now();
        var expiresIn = 300L; // 5 min
        var claims = JwtClaimsSet.builder()
                .issuer("AedesControlBackend")
                .subject(user.getId().toString())
                .claim("userName", user.getUserName())
                .claim("email", user.getEmail())
                .issuedAt(now).expiresAt(now.plusSeconds(expiresIn)).build();

        String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        CookieService.setCookie(response, "token", jwtToken, 300);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        CookieService.clearTokenCookie(response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(HttpServletRequest request) {
        String token = CookieService.getCookie(request, "token");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Jwt jwt;
        try {
            jwt = jwtDecoder.decode(token);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO userDto = new UserDTO();
        userDto.setId(UUID.fromString(jwt.getSubject()));
        userDto.setUserName(jwt.getClaimAsString("userName"));
        userDto.setEmail(jwt.getClaimAsString("email"));

        return ResponseEntity.ok(userDto);
    }

}
