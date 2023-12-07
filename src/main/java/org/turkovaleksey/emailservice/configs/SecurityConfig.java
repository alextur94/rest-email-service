package org.turkovaleksey.emailservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity instance to configure.
     * @return A SecurityFilterChain with the specified security configurations.
     * @throws Exception If an exception occurs during the configuration process.
     * @see HttpSecurity
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/email/download").authenticated()
                .requestMatchers("/api/email/download-period").authenticated()
                .requestMatchers("/api/email/save").permitAll()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }

    /**
     * Configures the user details service with an in-memory user.
     *
     * @return An instance of InMemoryUserDetailsManager with a pre-configured user.
     * @see UserDetailsService
     * @see InMemoryUserDetailsManager
     * @see User
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$txAB2TGjaZDqgw/DGJSjwuZSxq4ZlbmibnP8eFyfGaFAaCCKBMT9.")
                .roles("USER")
                .build()
        );
    }
}
