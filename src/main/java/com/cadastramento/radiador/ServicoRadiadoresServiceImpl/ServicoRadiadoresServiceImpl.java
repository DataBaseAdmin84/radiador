package com.cadastramento.radiador.ServicoRadiadoresServiceImpl; // Certifique-se de que o pacote está correto

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.repository.ServicoRadiadoresRepository;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}