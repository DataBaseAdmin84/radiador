package com.cadastramento.radiador.repository; // Certifique-se de que o pacote está correto

import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRadiadoresRepository extends JpaRepository<Servicoradiadores, Long> {
    // JpaRepository já fornece métodos como save(), findById(), findAll(), delete()
}