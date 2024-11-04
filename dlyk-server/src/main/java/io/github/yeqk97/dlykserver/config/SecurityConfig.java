package io.github.yeqk97.dlykserver.config;

import io.github.yeqk97.dlykserver.config.filter.TokenVerifyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static io.github.yeqk97.dlykserver.utils.Constants.LOGIN_URI;
import static io.github.yeqk97.dlykserver.utils.Constants.LOGOUT_URI;

@Configuration
public class SecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(final AuthenticationSuccessHandler authenticationSuccessHandler, final AuthenticationFailureHandler authenticationFailureHandler, final LogoutSuccessHandler logoutSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource, final TokenVerifyFilter tokenVerifyFilter) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.loginProcessingUrl(LOGIN_URI)
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler);
                })
                .logout((logout) -> {
                    logout.logoutUrl(LOGOUT_URI)
                            .logoutSuccessHandler(logoutSuccessHandler);
                })
                .authorizeRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers(LOGIN_URI).permitAll().anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource);
                })
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }

}
