package com.example.kos.controller;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kos.models.Kos;
import com.example.kos.service.KosService;

@Controller
public class FrontIndexController {
    private final KosService kosService;

    // @Autowired
    public FrontIndexController(KosService kosService) {
        this.kosService = kosService;
    }

    @GetMapping("/")
    public String getAllKos(Model model) {
        List<Kos> allKos = kosService.getAllKos();
        List<Kos> limitedKos = allKos.size() > 3 ? allKos.subList(0, 3) : allKos;
        model.addAttribute("listkos", limitedKos);
        return "user/index";
    }

}
