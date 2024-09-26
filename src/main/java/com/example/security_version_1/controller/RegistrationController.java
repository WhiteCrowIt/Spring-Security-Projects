package com.example.security_version_1.controller;

import com.example.security_version_1.model.User;
import com.example.security_version_1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        if (!user.getPassword().equals(user.getPassword2())) {
            result.rejectValue("password2", "error.user", "Passwords do not match");
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("errorMessage", "User with email: " + user.getEmail() + " already exists");
            return "registration";
        }

        return "redirect:/login";
    }

}



















