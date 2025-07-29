package com.cadastramento.radiador.repository;

import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicoRadiadoresRepository extends JpaRepository<Servicoradiadores, Long> {
    List<Servicoradiadores> findByData(LocalDate data);
    List<Servicoradiadores> findByDataBetween(LocalDate start, LocalDate end);}