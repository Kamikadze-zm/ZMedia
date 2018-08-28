package ru.kamikadze_zm.zmedia.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kamikadze_zm.zmedia.model.entity.User;

public class AuthenticationUtil {

    public static User getAuthenticatedUser() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        return (User) auth.getPrincipal();
    }

    public static boolean isAdmin() {
        User user = getAuthenticatedUser();
        if (user == null) {
            return false;
        }
        return user.isAdmin();
    }

    public static boolean compareUserEmail(String email) {
        User user = getAuthenticatedUser();
        if (user == null) {
            return false;
        }
        String authEmail = user.getEmail();
        return authEmail.equalsIgnoreCase(email);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return getAuthentication() != null;
    }

    public static boolean isAnonymous() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return false;
        }
        return AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }

    public static String getPrincipalName() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        return auth.getName();
    }
}
