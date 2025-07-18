package com.cadastramento.radiador.repository;

import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRadiadoresRepository extends JpaRepository<Servicoradiadores, Long> {
}