package com.cadastramento.radiador.ServicoRadiadoresServiceImpl;

import com.cadastramento.radiador.DTO.RadiadorDTO;
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
    @Override
    public BigDecimal somarValoresPorSemana(LocalDate data) {
        LocalDate startOfWeek = data.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = data.with(java.time.DayOfWeek.SUNDAY);
        return servicoRadiadoresRepository.findByDataBetween(startOfWeek, endOfWeek)
                .stream()
                .map(servico -> BigDecimal.valueOf(servico.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public BigDecimal somarValoresPorMes(LocalDate data) {
        LocalDate startOfMonth = data.withDayOfMonth(1);
        LocalDate endOfMonth = data.withDayOfMonth(data.lengthOfMonth());
        return servicoRadiadoresRepository.findByDataBetween(startOfMonth, endOfMonth)
                .stream()
                .map(servico -> BigDecimal.valueOf(servico.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<RadiadorDTO> buscarRadiadoresPorSemana(LocalDate data) {
        return List.of();
    }

    @Override
    public List<RadiadorDTO> buscarRadiadoresPorMes(LocalDate data) {
        return List.of();
    }

}