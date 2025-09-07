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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/api/faturamento")
    @ResponseBody
    public List<Map<String, Object>> getFaturamentoParaGrafico(@RequestParam(name = "periodo", defaultValue = "7d") String periodo) {
        List<FaturamentoDiarioDTO> faturamento;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        switch (periodo) {
            case "30d":
                faturamento = servicoRadiadoresService.getFaturamentoDosUltimosDias(30);
                break;
            case "this_month":
                faturamento = servicoRadiadoresService.getFaturamentoMesCorrente();
                break;
            case "7d":
            default:
                faturamento = servicoRadiadoresService.getFaturamentoDosUltimosDias(7);
                break;
        }

        return faturamento.stream()
                .map(dto -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("data", dto.getData().format(formatter));
                    map.put("total", dto.getTotal());
                    return map;
                })
                .collect(Collectors.toList());
    }
}