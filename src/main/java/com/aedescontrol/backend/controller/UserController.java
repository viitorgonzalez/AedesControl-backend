package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.LoginRequest;
import com.aedescontrol.backend.dto.UserDTO;
import com.aedescontrol.backend.model.User;
import com.aedescontrol.backend.repository.UserRepository;
import com.aedescontrol.backend.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
        long start = System.currentTimeMillis();
        log.info("POST /auth/login - Início da requisição, email={}", loginRequest.email());

        try {
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
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .build();

            String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            CookieService.setCookie(response, "token", jwtToken, 300);

            long elapsed = System.currentTimeMillis() - start;
            log.info("POST /auth/login - Login bem-sucedido para userId={} em {}ms", user.getId(), elapsed);
            return ResponseEntity.ok().build();

        } catch (BadCredentialsException e) {
            long elapsed = System.currentTimeMillis() - start;
            log.warn("POST /auth/login - Falha no login para email={} em {}ms: {}", loginRequest.email(), elapsed, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("POST /auth/login - Erro inesperado em {}ms", elapsed, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        log.info("POST /auth/logout - Início da requisição");

        try {
            CookieService.clearTokenCookie(response);
            long elapsed = System.currentTimeMillis() - start;
            log.info("POST /auth/logout - Logout concluído em {}ms", elapsed);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("POST /auth/logout - Erro inesperado em {}ms", elapsed, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        log.info("GET /auth/me - Início da requisição");

        try {
            String token = CookieService.getCookie(request, "token");
            if (token == null) {
                log.warn("GET /auth/me - Token não encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Jwt jwt;
            try {
                jwt = jwtDecoder.decode(token);
            } catch (JwtException e) {
                log.warn("GET /auth/me - Token inválido: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserDTO userDto = new UserDTO();
            userDto.setId(UUID.fromString(jwt.getSubject()));
            userDto.setUserName(jwt.getClaimAsString("userName"));
            userDto.setEmail(jwt.getClaimAsString("email"));

            long elapsed = System.currentTimeMillis() - start;
            log.info("GET /auth/me - Sucesso para userId={} em {}ms", userDto.getId(), elapsed);

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("GET /auth/me - Erro inesperado em {}ms", elapsed, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
