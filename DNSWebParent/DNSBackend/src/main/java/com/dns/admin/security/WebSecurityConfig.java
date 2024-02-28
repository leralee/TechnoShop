package com.dns.admin.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig{
    @Bean
    UserDetailsService userDetailsService() {
        return new DnsUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").hasAuthority("Админ")
                        .requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Админ", "Редактор")
                        .requestMatchers("/products/**").hasAnyAuthority("Админ", "Продавец", "Редактор", "Отправитель")
                        .requestMatchers("/questions/**").hasAnyAuthority("Админ", "Ассистент")
                        .requestMatchers("/reviews/**").hasAnyAuthority("Админ", "Ассистент")
                        .requestMatchers("/customers/**").hasAnyAuthority("Админ", "Продавец")
                        .requestMatchers("/shipping/**").hasAnyAuthority("Админ", "Продавец")
                        .requestMatchers("/orders/**").hasAnyAuthority("Админ", "Продавец", "Отправитель")
                        .requestMatchers("/reports/**").hasAnyAuthority("Админ", "Продавец")
                        .requestMatchers("/articles/**").hasAnyAuthority("Админ", "Редактор")
                        .requestMatchers("/menus/**").hasAnyAuthority("Админ", "Редактор")
                        .requestMatchers("/settings/**").hasAuthority("Админ")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                )
                .logout((LogoutConfigurer::permitAll))
                .rememberMe((remember) -> remember.key("springRocks")
                        .tokenValiditySeconds(7 * 24 * 60 * 60));



        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
