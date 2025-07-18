package com.cadastramento.radiador.ServicoRadiadoresController; // Certifique-se de que o pacote está correto

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import jakarta.validation.Valid; // Importar jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Importar BindingResult
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos")
public class ServicoRadiadoresController {

    @Autowired
    private ServicoRadiadoresService servicoRadiadoresService;

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("servico", new Servicoradiadores());
        return "form-servico";
    }

    @PostMapping
    public String salvarServico(@Valid @ModelAttribute("servico") Servicoradiadores servico, // Adicione @Valid
                                BindingResult result, // Adicione BindingResult
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Se houver erros de validação, você pode adicioná-los ao modelo
            // para exibir no formulário ou redirecionar com mensagens de erro.
            // Para Thymeleaf, você pode adicionar 'result' ao model ou usar flash attributes.
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servico", result);
            redirectAttributes.addFlashAttribute("servico", servico); // Para reter os dados preenchidos
            redirectAttributes.addFlashAttribute("mensagemErro", "Verifique os campos do formulário.");
            return "redirect:/servicos/novo"; // Redireciona de volta para o formulário
        }

        try {
            servicoRadiadoresService.salvarServico(servico);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Serviço cadastrado com sucesso!");
            return "redirect:/servicos/novo";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar serviço: " + e.getMessage());
            return "redirect:/servicos/novo";
        }
    }
}