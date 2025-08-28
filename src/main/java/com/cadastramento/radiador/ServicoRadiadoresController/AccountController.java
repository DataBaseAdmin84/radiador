package com.cadastramento.radiador.ServicoRadiadoresController;

import com.cadastramento.radiador.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/conta")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mudar-senha")
    public String showChangePasswordForm() {
        return "conta/mudar-senha";
    }

    @PostMapping("/mudar-senha")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "A nova senha e a confirmação não correspondem.");
            return "redirect:/conta/mudar-senha";
        }
        try {
            userService.changePassword(principal.getName(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("success", "Senha alterada com sucesso!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/conta/mudar-senha";
        }
    }
}