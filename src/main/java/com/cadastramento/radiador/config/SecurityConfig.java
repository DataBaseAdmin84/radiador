package com.cadastramento.radiador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/css/**", "/js/**").permitAll() // Permite acesso a arquivos estáticos
                .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
            )
            .formLogin(form -> form
                .loginPage("/login") // Define a URL da nossa página de login customizada
                .defaultSuccessUrl("/", true) // Redireciona para o dashboard após o login
                .permitAll() // Permite que todos acessem a página de login
            )
            .logout(logout -> logout.permitAll()); // Permite que todos façam logout

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Para começar, criaremos um usuário em memória.
        // Futuramente, podemos buscar os usuários do banco de dados.
        UserDetails user = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin")) // Senha é "admin"
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt para codificar as senhas de forma segura.
        return new BCryptPasswordEncoder();
    }
}