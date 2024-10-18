package io.github.yeqk97.dlykserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(final AuthenticationSuccessHandler authenticationSuccessHandler, final AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.loginProcessingUrl("/api/login")
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler);
                })
                .authorizeRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/api/login").permitAll().anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource);
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new UrlBasedCorsConfigurationSource();
    }

}
