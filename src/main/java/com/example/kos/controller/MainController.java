package com.example.kos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kos.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        String username = user.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);
        return "admin/index"; // template admin/dashboard.html
    }

    // @GetMapping("/login")
    // public String getLogin() {
    // return "login";
    // }

}
