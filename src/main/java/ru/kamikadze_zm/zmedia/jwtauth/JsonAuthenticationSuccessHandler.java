package ru.kamikadze_zm.zmedia.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.kamikadze_zm.zmedia.model.entity.User;

public class JsonAuthenticationSuccessHandler extends JsonHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    public JsonAuthenticationSuccessHandler(TokenService tokenService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        String accessToken = tokenService.getAccessToken(user);
        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = tokenService.getRefreshToken(user.getEmail(), refreshTokenId);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        write(response, HttpServletResponse.SC_OK, tokenMap);

        tokenService.saveRefreshToken(refreshTokenId, user.getEmail(), refreshToken);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session during the authentication process..
     *
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
