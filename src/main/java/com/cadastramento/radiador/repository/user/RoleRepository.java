package com.cadastramento.radiador.repository.user;

import com.cadastramento.radiador.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Método para buscar uma permissão pelo nome
    Role findByName(String name);
}