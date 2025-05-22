package com.example.kos.controller;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kos.service.KosService;

@Controller
public class FrontController {

    private final KosService kosService;

    // @Autowired

    public FrontController(KosService kosService) {
        this.kosService = kosService;
    }

    @GetMapping("/rooms")
    public String getAllKos(Model model) {
        model.addAttribute("listkos", kosService.getAllKos());
        return "user/rooms";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "user/about";
    }

}
