package ru.kamikadze_zm.zmedia.jwtauth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

public class JwtLogoutFilter extends GenericFilterBean {

    private final RequestMatcher logoutRequestMatcher;
    private final TokenService tokenService;

    public JwtLogoutFilter(String logoutUrl, TokenService tokenService) {
        this.logoutRequestMatcher = new AntPathRequestMatcher(logoutUrl);
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (requiresLogout(request, response)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String userEmail = auth.getName();
                String refreshTokenId = request.getParameter("refresh-token-id");
                if (refreshTokenId != null) {
                    tokenService.deleteUserRefreshToken(refreshTokenId, userEmail);
                } else {
                    tokenService.deleteUserRefreshTokens(userEmail);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().flush();
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        return logoutRequestMatcher.matches(request);
    }
}
