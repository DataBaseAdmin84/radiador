package com.cadastramento.radiador.ServicoRadiadoresServiceImpl;

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.repository.ServicoRadiadoresRepository;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoRadiadoresServiceImpl implements ServicoRadiadoresService {

    @Autowired
    private ServicoRadiadoresRepository servicoRadiadoresRepository;

    @Override
    public Servicoradiadores salvarServico(Servicoradiadores servico) {
        return servicoRadiadoresRepository.save(servico);
    }

    @Override
    public List<Servicoradiadores> buscarTodosServicos() {
        return servicoRadiadoresRepository.findAll();
    }

    @Override
    public Optional<Servicoradiadores> buscarServicoPorId(Long id) {
        return servicoRadiadoresRepository.findById(id);
    }

    @Override
    public void deletarServico(Long id) {
        servicoRadiadoresRepository.deleteById(id);
    }

    @Override
    public List<Servicoradiadores> listarTodos() {
        return servicoRadiadoresRepository.findAll();
    }
    @Override
    public BigDecimal somarValoresPorData(LocalDate data) {
        return servicoRadiadoresRepository.findByData(data)
                .stream()
                .map(servico -> BigDecimal.valueOf(servico.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}