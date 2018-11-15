package ru.kamikadze_zm.zmedia.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.model.entity.User.Role;

public class TokenService {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private static final String INVALID_REFRESH_TOKEN_MESSAGE = "Недопустимый токен обновления";

    private final TokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    public TokenService(TokenProperties tokenProperties, RefreshTokenRepository refreshTokenRepository, UserDetailsService userDetailsService) {
        this.tokenProperties = tokenProperties;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
    }

    public String getAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        this.setClaims(user, claims);
        Date date = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenProperties.getAccessTokenExpirationTime()))
                .signWith(SIGNATURE_ALGORITHM, tokenProperties.getSecretKey())
                .compact();
        return token;
    }

    public String getRefreshToken(String subject, String tokenId) {
        Date date = new Date();
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuer(tokenProperties.getIssuer())
                .setId(tokenId)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenProperties.getRefreshTokenExpirationTime()))
                .signWith(SIGNATURE_ALGORITHM, tokenProperties.getSecretKey())
                .compact();
        return token;
    }

    public void setClaims(User user, Claims claims) {
        claims.put(TokenClaims.NAME.getKey(), user.getName());
        if (user.getRole() != null) {
            claims.put(TokenClaims.ROLE.getKey(), user.getRole().toString());
        }
        claims.put(TokenClaims.AVATAR.getKey(), user.getAvatar());
        claims.put(TokenClaims.CONFIRMED.getKey(), user.getConfirmed());
    }

    public User mapClaimsToUser(Claims claims) {
        String email = claims.getSubject();
        String name = claims.get(TokenClaims.NAME.getKey(), String.class);
        Object roleClaim = claims.get(TokenClaims.ROLE.getKey());
        Role role = null;
        if (roleClaim != null) {
            role = Role.valueOf(roleClaim.toString().toUpperCase());
        }
        String avatar = claims.get(TokenClaims.AVATAR.getKey(), String.class);
        Boolean confirmed = claims.get(TokenClaims.CONFIRMED.getKey(), Boolean.class);
        return new User(email, name, avatar, role, confirmed);
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.length() < AUTHORIZATION_HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Incorrect authorization header");
        }
        return authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length(), authorizationHeader.length());
    }

    public Jws<Claims> parseToken(String token) throws
            ExpiredJwtException,
            MalformedJwtException,
            SignatureException,
            UnsupportedJwtException,
            IllegalArgumentException {
        return Jwts.parser().setSigningKey(tokenProperties.getSecretKey()).parseClaimsJws(token);
    }

    public void saveRefreshToken(String refreshTokenId, String userEmail, String refreshToken) {
        refreshTokenRepository.saveToken(refreshTokenId, userEmail, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) throws InvalidTokenException {
        Jws<Claims> claims;
        try {
            claims = parseToken(refreshToken);
        } catch (Exception e) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        String tokenId = claims.getBody().getId();
        if (tokenId == null) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        String tokenFromDB = refreshTokenRepository.getToken(tokenId);
        if (tokenFromDB == null) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        String email = claims.getBody().getSubject();
        if (email == null) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        if (!refreshToken.equals(tokenFromDB)) {
            deleteUserRefreshToken(tokenId, email);
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        User user;
        try {
            user = (User) userDetailsService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
        }

        return getAccessToken(user);
    }

    public void deleteUserRefreshToken(String tokenId, String userEmail) {
        refreshTokenRepository.deleteTokenByIdAndEmail(tokenId, userEmail);
    }

    public void deleteUserRefreshTokens(String userEmail) {
        refreshTokenRepository.deleteUserTokens(userEmail);
    }
}
