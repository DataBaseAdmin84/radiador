package com.cadastramento.radiador.service; // Certifique-se de que o pacote está correto

import com.cadastramento.radiador.model.Servicoradiadores;

import java.util.List;
import java.util.Optional;

public interface ServicoRadiadoresService {
    Servicoradiadores salvarServico(Servicoradiadores servico);
    List<Servicoradiadores> buscarTodosServicos();
    Optional<Servicoradiadores> buscarServicoPorId(Long id);
    void deletarServico(Long id);

}