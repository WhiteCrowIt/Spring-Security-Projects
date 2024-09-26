package com.example.security_version_1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String adminPanel() {
        return "admin_main";
    }
}
