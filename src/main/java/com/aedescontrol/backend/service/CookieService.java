package com.aedescontrol.backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CookieService {

    private static final String TOKEN_COOKIE = "token";

    private static final Logger log = LoggerFactory.getLogger(CookieService.class);

    public static void setCookie(HttpServletResponse response, String key, String value, int seconds) {
        log.debug("Criando cookie: key='{}', maxAge={} segundos, httpOnly=true, path='/'", key, seconds);
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(seconds);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        log.debug("Cookie {} adicionado no HTTP", key);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        log.debug("Buscando cookie: key={}", key);
        if (request.getCookies() == null) {
            log.warn("Nenhum cookie presente na requisição. Cookie key={} não encontrado.", key);
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> key.equals(cookie.getName()))
                .map(cookie -> {
                    log.debug("Cookie encontrado: key={}", key);
                    return cookie.getValue();
                })
                .findFirst()
                .orElseGet(() -> {
                    log.warn("Cookie não encontrado: key={}", key);
                    return null;
                });
    }

    public static void clearTokenCookie(HttpServletResponse response) {
        log.debug("Iniciando limpeza do cookie '{}'", TOKEN_COOKIE);
        Cookie cookie = new Cookie(TOKEN_COOKIE, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.debug("Cookie '{}' limpo com sucesso.", TOKEN_COOKIE);
    }
}
