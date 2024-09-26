package com.example.security_version_1.config;

import com.example.security_version_1.model.User;
import com.example.security_version_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration", "/forgot_password", "/static/**", "/css/**", "/images/**", "/js/**", "/webjars/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Доступ к /admin только для администраторов
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email") // Указываем, что поле для логина называется "email"
                        .passwordParameter("password") // Указываем имя поля для пароля
                        .permitAll()
                        .defaultSuccessUrl("/main") // Перенаправление на страницу пользователя после успешного входа
                        .successHandler((request, response, authentication) -> {
                            User user = (User) authentication.getPrincipal();
                            if (user.isAdmin()) {
                                response.sendRedirect("/admin"); // Перенаправление на админ-страницу для администраторов
                            } else {
                                response.sendRedirect("/main"); // Перенаправление для обычных пользователей
                            }
                        })
                        .failureUrl("/login?error") // Перенаправление при ошибке входа
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout") // URL для выхода из системы
                        .logoutSuccessUrl("/login?logout") // URL для перенаправления после выхода
                        .permitAll());

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }



}
