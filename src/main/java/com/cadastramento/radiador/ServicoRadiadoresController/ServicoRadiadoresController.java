package com.cadastramento.radiador.ServicoRadiadoresController; // Certifique-se de que o pacote está correto

import com.cadastramento.radiador.model.Servicoradiadores;
import com.cadastramento.radiador.service.ServicoRadiadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos") // Define um prefixo para todas as URLs neste controller
public class ServicoRadiadoresController {

    @Autowired
    private ServicoRadiadoresService servicoRadiadoresService;

    /**
     * Exibe o formulário para cadastrar um novo serviço de radiadores.
     * Mapeia para GET /servicos/novo
     */
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        // Adiciona um objeto Servicoradiadores vazio ao modelo
        // Este objeto será preenchido pelos dados do formulário
        model.addAttribute("servico", new Servicoradiadores());
        // Retorna o nome da view (seu arquivo HTML)
        return "form-servico"; // Deve corresponder ao nome do seu arquivo HTML (ex: form-servico.html)
    }

    /**
     * Processa a submissão do formulário para salvar um novo serviço.
     * Mapeia para POST /servicos
     */
    @PostMapping
    public String salvarServico(@ModelAttribute("servico") Servicoradiadores servico,
                                RedirectAttributes redirectAttributes) {
        try {
            servicoRadiadoresService.salvarServico(servico);
            // Adiciona uma mensagem de sucesso para ser exibida após o redirecionamento
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Serviço cadastrado com sucesso!");
            // Redireciona para uma página de sucesso ou lista de serviços
            return "redirect:/servicos/novo"; // Ou "redirect:/servicos/lista" se você tiver uma
        } catch (Exception e) {
            // Adiciona uma mensagem de erro em caso de falha
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar serviço: " + e.getMessage());
            // Redireciona de volta para o formulário ou uma página de erro
            return "redirect:/servicos/novo";
        }
    }

    // --- Exemplo de um endpoint para listar serviços (opcional) ---
    /*
    @GetMapping("/lista")
    public String listarServicos(Model model) {
        List<Servicoradiadores> servicos = servicoRadiadoresService.buscarTodosServicos();
        model.addAttribute("listaServicos", servicos);
        return "lista-servicos"; // Nome da sua view para listar serviços
    }
    */
}