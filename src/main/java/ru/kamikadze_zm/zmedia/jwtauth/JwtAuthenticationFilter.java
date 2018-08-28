package ru.kamikadze_zm.zmedia.jwtauth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final static String TOKEN_HEADER = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String authorizationHeader = request.getHeader(TOKEN_HEADER);
        if (authorizationHeader != null) {
            try {
                String token = tokenService.extractToken(authorizationHeader);
                Authentication auth = authenticationManager.authenticate(new JwtAuthenticationToken(token));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (AuthenticationException authenticationException) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Jwt authentication exception:", authenticationException);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
