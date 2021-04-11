package ru.itis.trofimoff.todoapp.security.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private Logger logger;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        try {
            if (roles.contains("ADMIN")) {
                httpServletResponse.sendRedirect("admin");
            } else {
                httpServletResponse.sendRedirect("main");
            }
        } catch (IOException ex) {
            logger.error("Can't redirect; Exception {}; Info: {}", ex, ex.getMessage());
        }
    }
}