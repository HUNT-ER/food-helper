package com.boldyrev.foodhelper.security.config;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(request -> request.anyRequest().authenticated())
            .authorizeHttpRequests(request -> request.anyRequest().permitAll());

//
//            .csrf().disable().authorizeHttpRequests()
//            .requestMatchers(HttpMethod.POST, USER_AUTHENTICATION_POST_ENDPOINTS).permitAll()
//            .requestMatchers(HttpMethod.GET, MAIN_ENTITY_GET_ENDPOINTS).permitAll()
//            .anyRequest().authenticated().and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//            .build();

//        security.authorizeHttpRequests(
//                req -> req.anyRequest().permitAll())
//            .csrf(csrf -> csrf.disable())
//            .addFilterBefore(new DeniedClientFilter(), DisableEncodeUrlFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName("preferred_username");
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = (List<String>) jwt.getClaimAsMap("realm_access")
                .get("roles");

            return Stream.concat(authorities.stream(),
                roles.stream()
                    .filter(role -> role.startsWith("ROLE_"))
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
            ).toList();
        });

        return converter;
    }
}