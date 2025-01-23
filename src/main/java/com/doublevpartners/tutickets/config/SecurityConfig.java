package com.doublevpartners.tutickets.config;
import static com.doublevpartners.tutickets.util.Constants.PATH_ADMIN;
import static com.doublevpartners.tutickets.util.Constants.PATH_API_DOCS;
import static com.doublevpartners.tutickets.util.Constants.PATH_AUTH;
import static com.doublevpartners.tutickets.util.Constants.PATH_SWAGGER;
import static com.doublevpartners.tutickets.util.Constants.PATH_TICKETS;
import static com.doublevpartners.tutickets.util.Constants.PATH_VARIABLE_ID;

import com.doublevpartners.tutickets.filter.JwtRequestFilter;
import com.doublevpartners.tutickets.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtUtil jwtUtil;

  public SecurityConfig(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(PATH_AUTH, PATH_AUTH + PATH_ADMIN, PATH_SWAGGER, PATH_API_DOCS).permitAll()
        .requestMatchers(HttpMethod.DELETE, PATH_TICKETS + PATH_VARIABLE_ID).hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .exceptionHandling(exceptions -> exceptions
        .authenticationEntryPoint((request, response, authException) -> {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
          response.getWriter().write("{\"message\": \"Authentication required to access this resource.\"}");
        })
        .accessDeniedHandler((request, response, accessDeniedException) -> {
          response.setStatus(HttpStatus.FORBIDDEN.value());
          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
          response.getWriter().write("{\"message\": \"You do not have permission to access this resource.\"}");
        }))
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("brian712")
      .password(passwordEncoder().encode("U53rP@ssw0rd!2025"))
      .roles("USER")
      .build();

    UserDetails admin = User.withUsername("adminUser")
      .password(passwordEncoder().encode("Adm1nP@ssw0rd!2025"))
      .roles("ADMIN")
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public JwtRequestFilter jwtRequestFilter() {
    return new JwtRequestFilter(jwtUtil);
  }
}



