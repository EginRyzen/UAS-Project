package com.example.kos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kos.models.Kos;
import com.example.kos.models.User;
import com.example.kos.repository.KosRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private KosRepository kosRepository;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        String username = user.getUsername();
        model.addAttribute("username", username);

        long totalKos = kosRepository.count();
        long kosPutra = kosRepository.countByCategory("Putra");
        long kosPutri = kosRepository.countByCategory("Putri");
        long kosBebas = kosRepository.countByCategory("Bebas");

        model.addAttribute("totalKos", totalKos);
        model.addAttribute("kosPutra", kosPutra);
        model.addAttribute("kosPutri", kosPutri);
        model.addAttribute("kosBebas", kosBebas);

        return "admin/index";
    }

    @GetMapping("/admin/kos/print")
    public String printKos(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        String username = user.getUsername();
        model.addAttribute("username", username);

        List<Kos> daftarKos = kosRepository.findAll();
        model.addAttribute("daftarKos", daftarKos);

        return "admin/printpdf";
    }

}
