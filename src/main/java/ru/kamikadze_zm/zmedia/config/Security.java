package ru.kamikadze_zm.zmedia.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.kamikadze_zm.zmedia.jwtauth.JsonAuthenticationFailureHandler;
import ru.kamikadze_zm.zmedia.jwtauth.JsonAuthenticationFilter;
import ru.kamikadze_zm.zmedia.jwtauth.JsonAuthenticationSuccessHandler;
import ru.kamikadze_zm.zmedia.jwtauth.JwtAuthenticationFilter;
import ru.kamikadze_zm.zmedia.jwtauth.JwtAuthenticationProvider;
import ru.kamikadze_zm.zmedia.jwtauth.JwtLogoutFilter;
import ru.kamikadze_zm.zmedia.jwtauth.RefreshTokenRepository;
import ru.kamikadze_zm.zmedia.jwtauth.TokenProperties;
import ru.kamikadze_zm.zmedia.jwtauth.TokenService;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.kamikadze_zm.zmedia.jwtauth")
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                .headers().frameOptions().sameOrigin().and()
                .cors().and()
                .csrf().disable()
                .logout().disable()
                .addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtAuthenticationFilter(), RememberMeAuthenticationFilter.class)
                .addFilterAfter(jwtLogoutFilter(), RememberMeAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() {
        JsonAuthenticationFilter jaf = new JsonAuthenticationFilter("/api/login", objectMapper());
        jaf.setAuthenticationSuccessHandler(new JsonAuthenticationSuccessHandler(tokenService(), objectMapper()));
        jaf.setAuthenticationFailureHandler(new JsonAuthenticationFailureHandler(objectMapper()));
        jaf.setAuthenticationManager(authenticationManager());
        return jaf;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jaf = new JwtAuthenticationFilter(authenticationManager(), tokenService());
        return jaf;
    }

    @Bean
    public JwtLogoutFilter jwtLogoutFilter() {
        JwtLogoutFilter jlf = new JwtLogoutFilter("/api/logout", tokenService());
        return jlf;
    }

    @Bean
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        JsonAuthenticationSuccessHandler jash = new JsonAuthenticationSuccessHandler(tokenService(), objectMapper());
        return jash;
    }

    @Bean
    public TokenService tokenService() {
        TokenService ts = new TokenService(tokenProperties(), refreshTokenRepository(), userDetailsService());
        return ts;
    }

    @Bean
    public TokenProperties tokenProperties() {
        TokenProperties tp = new TokenProperties(
                Long.parseLong(env.getProperty("jwt.accessTokenExpirationTime")),
                Long.parseLong(env.getProperty("jwt.refreshTokenExpirationTime")),
                env.getProperty("jwt.issuer"),
                env.getProperty("jwt.secretKey"));
        return tp;
    }

    @Bean
    public RefreshTokenRepository refreshTokenRepository() {
        RefreshTokenRepository rtr = new RefreshTokenRepository(dataSource);
        return rtr;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new ru.kamikadze_zm.zmedia.service.impl.UserServiceImpl();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
        ap.setUserDetailsService(userDetailsService());
        ap.setPasswordEncoder(passwordEncoder());
        return ap;
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider ap = new JwtAuthenticationProvider(tokenService());
        return ap;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(daoAuthenticationProvider());
        authenticationProviders.add(jwtAuthenticationProvider());
        AuthenticationManager am = new ProviderManager(authenticationProviders);
        return am;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
