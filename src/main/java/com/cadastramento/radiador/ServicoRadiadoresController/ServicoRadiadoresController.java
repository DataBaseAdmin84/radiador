package com.cadastramento.radiador.ServicoRadiadoresController;

import com.cadastramento.radiador.DTO.RadiadorDTO;
import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.PdfGenerationService;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/servicos")
public class ServicoRadiadoresController {

    @Autowired
    private ServicoRadiadoresService servicoRadiadoresService;

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @GetMapping
    public String exibirFormularioEListagem(@RequestParam(name = "termo", required = false) String termo, Model model) {
        if (!model.containsAttribute("servico")) {
            model.addAttribute("servico", new Servicoradiadores());
        }

        List<Servicoradiadores> servicos;
        if (termo != null && !termo.trim().isEmpty()) {
            servicos = servicoRadiadoresService.searchByTerm(termo);
        } else {
            servicos = servicoRadiadoresService.listarTodos();
        }

        model.addAttribute("termo", termo); // Passa o termo de volta para o formulário
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

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Servicoradiadores> servicoOpt = servicoRadiadoresService.buscarServicoPorId(id);
        if (servicoOpt.isPresent()){
            model.addAttribute("servico", servicoOpt.get());
            return "editar-servico";
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Serviço não encontrado.");
            return "redirect:/servicos";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizarServico(@PathVariable Long id,
                                   @Valid @ModelAttribute("servico") Servicoradiadores servico,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editar-servico";
        }

        servico.setId(id);
        servicoRadiadoresService.salvarServico(servico);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Serviço atualizado com sucesso!");
        return "redirect:/servicos";
    }

    @PostMapping("/deletar/{id}")
    public String deletarServico(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        servicoRadiadoresService.deletarServico(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Serviço excluído com sucesso!");
        return "redirect:/servicos";
    }

    @GetMapping("/soma-dia")
    public String mostrarSomaPorDia(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        BigDecimal soma = servicoRadiadoresService.somarValoresPorData(data);
        List<RadiadorDTO> radiadores = servicoRadiadoresService.buscarRadiadoresPorDia(data);
        model.addAttribute("radiadores", radiadores);
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
    @GetMapping("/soma-mes") // Corrigido: adicionada a barra inicial
    public String somaMes(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        BigDecimal somaMes = servicoRadiadoresService.somarValoresPorMes(data);
        model.addAttribute("somaMes", somaMes);
        model.addAttribute("dataMes", data);
        return "soma-dia";
    }

    @GetMapping("/relatorio-semana")
    public String relatorioSemana(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        // 1. Busca a lista de serviços
        List<RadiadorDTO> servicosDaSemana = servicoRadiadoresService.buscarRadiadoresPorSemana(data);

        // 2. Calcula as datas de início e fim da semana para o título
        WeekFields weekFields = WeekFields.of(java.util.Locale.getDefault());
        LocalDate dataInicio = data.with(weekFields.dayOfWeek(), 1);
        LocalDate dataFim = dataInicio.plusDays(6);

        // 3. Adiciona os dados ao model com os nomes que o HTML espera
        model.addAttribute("servicosDaSemana", servicosDaSemana); // Nome corrigido
        model.addAttribute("dataInicio", dataInicio); // Data de início adicionada
        model.addAttribute("data", data); // Passa a data original para o link do PDF
        model.addAttribute("dataFim", dataFim);       // Data de fim adicionada

        return "relatorio-semana";
    }

    @GetMapping("/relatorio-mensal")
    public String relatorioMes(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Model model) {
        List<RadiadorDTO> servicosDoMes = servicoRadiadoresService.buscarRadiadoresPorMes(data);

        // Adiciona os dados ao model com os nomes corretos que o HTML espera
        model.addAttribute("servicosDoMes", servicosDoMes); // Nome corrigido
        model.addAttribute("dataInicio", data.with(TemporalAdjusters.firstDayOfMonth()));
        model.addAttribute("dataFim", data.with(TemporalAdjusters.lastDayOfMonth()));
        model.addAttribute("data", data); // Para o título do relatório mensal
        return "relatorio-mensal";
    }

    @GetMapping("/relatorio-semana/pdf")
    public ResponseEntity<byte[]> gerarPdfRelatorioSemana(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<RadiadorDTO> servicosDaSemana = servicoRadiadoresService.buscarRadiadoresPorSemana(data);

        WeekFields weekFields = WeekFields.of(java.util.Locale.getDefault());
        LocalDate dataInicio = data.with(weekFields.dayOfWeek(), 1);
        LocalDate dataFim = dataInicio.plusDays(6);

        // Prepara os dados para o template
        Map<String, Object> modelAttributes = Map.of(
                "servicosDaSemana", servicosDaSemana,
                "dataInicio", dataInicio,
                "dataFim", dataFim,
                "isPdfMode", true
        );

        ByteArrayOutputStream pdfStream = pdfGenerationService.generatePdfFromTemplate("relatorio-semana", modelAttributes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "relatorio-semanal-" + data.toString() + ".pdf";
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok().headers(headers).body(pdfStream.toByteArray());
    }

}