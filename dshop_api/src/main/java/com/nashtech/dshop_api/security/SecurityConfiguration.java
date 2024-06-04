package com.nashtech.dshop_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    
    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    private final CustomerAccessDeniedHandler customerAccessDeniedHandler;

    public SecurityConfiguration(CustomerAccessDeniedHandler customerAccessDeniedHandler) {
        this.customerAccessDeniedHandler = customerAccessDeniedHandler;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter authFilter) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                    .requestMatchers(HttpMethod.POST, this.baseUrl + "/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, this.baseUrl + "/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, this.baseUrl + "/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST,this.baseUrl + "/products/buy").hasRole("CUSTOMER")
                    .requestMatchers(HttpMethod.POST, this.baseUrl + "/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, this.baseUrl + "/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, this.baseUrl + "/categories/**").permitAll()
                    .requestMatchers(HttpMethod.POST, this.baseUrl + "/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, this.baseUrl + "/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/categories/**").hasRole("ADMIN")
                    .requestMatchers(this.baseUrl + "/reviews/**").permitAll()
                    .requestMatchers("/uploads/**").permitAll()
                    .anyRequest().authenticated()
                    
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(this.customerAccessDeniedHandler))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   
    }
}
