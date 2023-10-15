package com.example.ExampleJWT.configuration;

import com.example.ExampleJWT.entity.Token;
import com.example.ExampleJWT.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public void logout
            (HttpServletRequest request,
             HttpServletResponse response,
             Authentication authentication) {
        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        if (header == null || !header.startsWith("Bearer "))
        {
            return;
        }
        jwtToken=header.substring(7);
        Token token= tokenRepository.findByToken(jwtToken)
                .orElse(null);
        if (token !=null)
        {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }
}
