package ru.kamikadze_zm.zmedia.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String POST_METHOD_NAME = "POST";

    private final ObjectMapper objectMapper;

    public JsonAuthenticationFilter(String defaultFilterProcessesUrl, ObjectMapper mapper) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, POST_METHOD_NAME));
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (!request.getMethod().equals(POST_METHOD_NAME)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        UserCredentials credentials;
        try {
            credentials = objectMapper.readValue(request.getReader(), UserCredentials.class);
        } catch (IOException e) {
            throw new BadCredentialsException("Incorrect credentials");
        }

        String email = credentials.getEmail();
        String password = credentials.getPassword();
        if (email == null) {
            email = "";
        }
        if (password == null) {
            password = "";
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
