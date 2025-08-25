package com.cadastramento.radiador.ServicoRadiadoresController;

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class DashboardController {

    @Autowired
    private ServicoRadiadoresService servicoRadiadoresService;

    @GetMapping("/")
    public String exibirDashboard(Model model) {
        LocalDate hoje = LocalDate.now();

        // 1. Buscar os totais
        BigDecimal somaHoje = servicoRadiadoresService.somarValoresPorData(hoje);
        BigDecimal somaSemana = servicoRadiadoresService.somarValoresPorSemana(hoje);
        BigDecimal somaMes = servicoRadiadoresService.somarValoresPorMes(hoje);

        // 2. Buscar os 5 serviços mais recentes
        Page<Servicoradiadores> ultimosServicosPage = servicoRadiadoresService.listarTodos(
                PageRequest.of(0, 5, Sort.by("data").descending())
        );

        // 3. Adicionar tudo ao modelo
        model.addAttribute("somaHoje", somaHoje);
        model.addAttribute("somaSemana", somaSemana);
        model.addAttribute("somaMes", somaMes);
        model.addAttribute("ultimosServicos", ultimosServicosPage.getContent());

        return "dashboard";
    }
}