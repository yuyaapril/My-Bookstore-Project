package com.example.bookstore.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.bookstore.services.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class WebSecurityConfig {
   @Autowired
   private final UserService userService;

   @Bean
   public UserDetailsService userDetailsService() {
     return userService;
   }

   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationProvider authenticationProvider() {
     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
     provider.setUserDetailsService(userDetailsService());
     provider.setPasswordEncoder(passwordEncoder());
     return provider;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
               .csrf(AbstractHttpConfigurer::disable)
               .formLogin(httpForm -> {
                   httpForm.loginPage("/login").permitAll();
                   httpForm.defaultSuccessUrl("/");
               })
               .authorizeRequests(registry ->{
                registry.requestMatchers("/login", "/register","/css/**","/js/**","/imgs/**").permitAll();
                registry.anyRequest().authenticated();
               })
               .build();
   }


}
