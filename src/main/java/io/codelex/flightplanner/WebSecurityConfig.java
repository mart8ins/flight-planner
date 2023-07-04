package io.codelex.flightplanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/admin-api/flights").authenticated()
//                .requestMatchers("/admin-api/flights/{flightId}").authenticated()
                .anyRequest().permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
