package com.cadastramento.radiador.ServicoRadiadoresController;

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/servicos")
public class ServicoRadiadoresController {

    @Autowired
    private ServicoRadiadoresService servicoRadiadoresService;

    @GetMapping
    public String exibirFormularioEListagem(Model model) {
        if (!model.containsAttribute("servico")) {
            model.addAttribute("servico", new Servicoradiadores());
        }
        List<Servicoradiadores> servicos = servicoRadiadoresService.listarTodos();
        model.addAttribute("servicos", servicos);
        return "form-servico";
    }

    @PostMapping
    public String salvarServico(@Valid @ModelAttribute("servico") Servicoradiadores servico,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servico", result);
            redirectAttributes.addFlashAttribute("servico", servico);
            redirectAttributes.addFlashAttribute("mensagemErro", "Verifique os campos do formulário.");
            return "redirect:/servicos";
        }

        try {
            servicoRadiadoresService.salvarServico(servico);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Serviço cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar serviço: " + e.getMessage());
        }
        return "redirect:/servicos";
    }
    @GetMapping("/soma-dia")
    public String mostrarSomaPorDia(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        BigDecimal soma = servicoRadiadoresService.somarValoresPorData(data);
        model.addAttribute("soma", soma);
        model.addAttribute("data", data);
        return "soma-dia";
    }
    @GetMapping("/soma-semana")
    public String somaSemana(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        BigDecimal somaSemana = servicoRadiadoresService.somarValoresPorSemana(data);
        model.addAttribute("somaSemana", somaSemana);
        model.addAttribute("dataSemana", data);
        return "soma-dia";
    }
    @GetMapping("soma-mes")
    public String somaMes(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        BigDecimal somaMes = servicoRadiadoresService.somarValoresPorMes(data);
        model.addAttribute("somaMes", somaMes);
        model.addAttribute("dataMes", data);
        return "soma-dia";
    }

}