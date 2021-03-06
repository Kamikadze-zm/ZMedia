package ru.kamikadze_zm.zmedia.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import ru.kamikadze_zm.zmedia.model.entity.User;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LogManager.getLogger(JwtAuthenticationProvider.class);

    private final TokenService tokenService;

    public JwtAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = (String) authentication.getCredentials();

        Jws<Claims> jwsClaims;
        try {
            jwsClaims = tokenService.parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException(e.getMessage());
        }
        Claims claims = jwsClaims.getBody();
        User user = tokenService.mapClaimsToUser(claims);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Authenticated user: {}", user);
        }
        return new JwtAuthenticationToken(user, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
