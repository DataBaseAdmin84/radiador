package com.cadastramento.radiador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring irá injetar nosso CustomUserDetailsService automaticamente
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                // Apenas usuários com a ROLE_ADMIN podem acessar a rota de exclusão
                .requestMatchers(HttpMethod.POST, "/servicos/deletar/**").hasRole("ADMIN")
                .requestMatchers("/css/**", "/js/**").permitAll() // Permite acesso a arquivos estáticos
                .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
            )
            .formLogin(form -> form
                .loginPage("/login") // Define a URL da nossa página de login customizada
                .defaultSuccessUrl("/servicos", true) // Redireciona para a página de gerenciamento de serviços após o login
                .permitAll() // Permite que todos acessem a página de login
            )
            .logout(logout -> logout.permitAll()); // Permite que todos façam logout

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt para codificar as senhas de forma segura.
        return new BCryptPasswordEncoder();
    }
}