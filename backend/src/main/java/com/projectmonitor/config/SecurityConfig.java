//package com.projectmonitor.config;
//import com.projectmonitor.security.JwtAuthFilter; import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.*; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.*; import java.util.List;
//@Configuration @EnableMethodSecurity public class SecurityConfig {
// @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
// @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthFilter jwt)throws Exception{return http.csrf(c->c.disable()).cors(c->c.configurationSource(cors())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/health").permitAll().anyRequest().authenticated()).addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();}
// @Bean CorsConfigurationSource cors(){CorsConfiguration c=new CorsConfiguration();
//  c.setAllowedOrigins(List.of("http://localhost:5173","http://127.0.0.1:5173"));
//  c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
//  c.setAllowedHeaders(List.of("*"));
//  return r->{UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();
//   s.registerCorsConfiguration("/**",c);return s;};}
//}
package com.projectmonitor.config;

import com.projectmonitor.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

 @Bean
 PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Bean
 SecurityFilterChain filterChain(
         HttpSecurity http,
         JwtAuthFilter jwt) throws Exception {

  return http
          .csrf(c -> c.disable())
          .cors(c -> c.configurationSource(cors()))
          .sessionManagement(s ->
                  s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(a ->
                  a.requestMatchers(
                                  "/api/auth/**",
                                  "/api/health"
                          ).permitAll()
                          .anyRequest().authenticated())
          .addFilterBefore(
                  jwt,
                  UsernamePasswordAuthenticationFilter.class)
          .build();
 }

 @Bean
 CorsConfigurationSource cors() {

  CorsConfiguration c = new CorsConfiguration();

//  c.setAllowedOrigins(
//          List.of(
//                  "http://localhost:5173",
//                  "http://127.0.0.1:5173"
//          )
//  );

  c.setAllowedOrigins(
          List.of(
                  "http://localhost:5173",
                  "http://127.0.0.1:5173",
                  "https://projectmonitor.vercel.app"
          )
  );

  c.setAllowedMethods(
          List.of(
                  "GET",
                  "POST",
                  "PUT",
                  "DELETE",
                  "OPTIONS"
          )
  );

  c.setAllowedHeaders(List.of("*"));

  UrlBasedCorsConfigurationSource s =
          new UrlBasedCorsConfigurationSource();

  s.registerCorsConfiguration("/**", c);

  return s;
 }
}
