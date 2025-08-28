package com.cadastramento.radiador.repository.user;

import com.cadastramento.radiador.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar um usuário pelo seu nome de usuário
    Optional<User> findByUsername(String username);
}