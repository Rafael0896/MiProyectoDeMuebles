package edu.sena.creamuebles.controller.view;

import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthViewController {

    private final UserService userService;

    public AuthViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login"; // Devuelve templates/auth/login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO("", "", "", ""));
        return "auth/register"; // Devuelve templates/auth/register.html
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register"; // Si hay errores de validación, vuelve a mostrar el formulario
        }
        try {
            userService.registerNewUser(registrationDTO);
        } catch (IllegalStateException e) {
            // Si el email ya existe
            result.rejectValue("email", "email.exists", e.getMessage());
            return "auth/register";
        }
        return "redirect:/login?success"; // Redirige al login con un mensaje de éxito
    }
}