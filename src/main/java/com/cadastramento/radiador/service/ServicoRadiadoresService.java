package com.cadastramento.radiador.service;

import com.cadastramento.radiador.model.Servicoradiadores;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServicoRadiadoresService {
    Servicoradiadores salvarServico(Servicoradiadores servico);
    List<Servicoradiadores> buscarTodosServicos();
    Optional<Servicoradiadores> buscarServicoPorId(Long id);
    void deletarServico(Long id);

    List<Servicoradiadores> listarTodos();
    java.math.BigDecimal somarValoresPorData(java.time.LocalDate data);
    BigDecimal somarValoresPorSemana(LocalDate data);
    BigDecimal somarValoresPorMes(LocalDate data);

}