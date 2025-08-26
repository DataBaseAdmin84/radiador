package com.cadastramento.radiador.service;

import com.cadastramento.radiador.DTO.FaturamentoDiarioDTO;
import com.cadastramento.radiador.DTO.RadiadorDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServicoRadiadoresService {
    Servicoradiadores salvarServico(Servicoradiadores servico);
    Optional<Servicoradiadores> buscarServicoPorId(Long id);
    void deletarServico(Long id);
    Page<Servicoradiadores> listarTodos(Pageable pageable);
    Page<Servicoradiadores> searchByTerm(String termo, Pageable pageable);

    // Métodos para Somas
    BigDecimal somarValoresPorData(LocalDate data);
    BigDecimal somarValoresPorSemana(LocalDate data);
    BigDecimal somarValoresPorMes(LocalDate data);

    // Métodos para Relatórios e Listagens
    List<RadiadorDTO> buscarRadiadoresPorDia(LocalDate data);
    List<RadiadorDTO> buscarRadiadoresPorSemana(LocalDate data);
    List<RadiadorDTO> buscarRadiadoresPorMes(LocalDate data);

    List<FaturamentoDiarioDTO> getFaturamentoDosUltimosDias(int dias);
}