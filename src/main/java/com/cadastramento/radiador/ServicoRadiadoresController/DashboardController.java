package com.cadastramento.radiador.ServicoRadiadoresController;

import com.cadastramento.radiador.DTO.FaturamentoDiarioDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/api/faturamento-ultimos-7-dias")
    @ResponseBody // Importante: Indica que o retorno é o corpo da resposta (JSON)
    public List<Map<String, Object>> getFaturamentoUltimosSeteDias() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        // 1. Chama o novo e eficiente método de serviço.
        List<FaturamentoDiarioDTO> faturamento = servicoRadiadoresService.getFaturamentoDosUltimosDias(7);

        // 2. Transform the DTO list into the Map structure that the JavaScript expects.
        return faturamento.stream()
                .map(dto -> {
                    // Criando o mapa explicitamente para evitar problemas de inferência de tipo
                    Map<String, Object> map = new HashMap<>();
                    map.put("data", dto.getData().format(formatter));
                    map.put("total", dto.getTotal());
                    return map;
                })
                .collect(Collectors.toList());
    }
}