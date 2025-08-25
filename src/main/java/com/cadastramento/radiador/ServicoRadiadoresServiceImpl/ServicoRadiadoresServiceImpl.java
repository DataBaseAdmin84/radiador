package com.cadastramento.radiador.ServicoRadiadoresServiceImpl;

import com.cadastramento.radiador.DTO.RadiadorDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.repository.ServicoRadiadoresRepository;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ServicoRadiadoresServiceImpl implements ServicoRadiadoresService {

    @Autowired
    private ServicoRadiadoresRepository repository;

    @Override
    public Servicoradiadores salvarServico(Servicoradiadores servico) {
        return repository.save(servico);
    }

    @Override
    public Optional<Servicoradiadores> buscarServicoPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deletarServico(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Servicoradiadores> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Servicoradiadores> searchByTerm(String termo, Pageable pageable) {
        return repository.searchByTerm(termo, pageable);
    }

    @Override
    public BigDecimal somarValoresPorData(LocalDate data) {
        return repository.sumPrecoByDataBetween(data, data);
    }

    @Override
    public BigDecimal somarValoresPorSemana(LocalDate data) {
        LocalDate inicioSemana = data.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate fimSemana = inicioSemana.plusDays(6);
        return repository.sumPrecoByDataBetween(inicioSemana, fimSemana);
    }

    @Override
    public BigDecimal somarValoresPorMes(LocalDate data) {
        LocalDate inicioMes = data.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = data.with(TemporalAdjusters.lastDayOfMonth());
        return repository.sumPrecoByDataBetween(inicioMes, fimMes);
    }

    @Override
    public List<RadiadorDTO> buscarRadiadoresPorDia(LocalDate data) {
        return repository.findServicosAsDTOByData(data);
    }

    @Override
    public List<RadiadorDTO> buscarRadiadoresPorSemana(LocalDate data) {
        // A lógica de cálculo de datas é a mesma da soma
        LocalDate inicioSemana = data.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate fimSemana = inicioSemana.plusDays(6);
        return repository.findServicosAsDTOByDataBetween(inicioSemana, fimSemana);
    }

    @Override
    public List<RadiadorDTO> buscarRadiadoresPorMes(LocalDate data) {
        // A lógica de cálculo de datas é a mesma da soma
        LocalDate inicioMes = data.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = data.with(TemporalAdjusters.lastDayOfMonth());
        return repository.findServicosAsDTOByDataBetween(inicioMes, fimMes);
    }
}