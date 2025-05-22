package com.example.crud.controller;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.crud.models.Kos;
import com.example.crud.service.KosService;

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

    @GetMapping(value = "/rooms", params = { "priceFilter", "typeFilter" })
public String filterKos(
        @RequestParam(required = false) String priceFilter,
        @RequestParam(required = false) String typeFilter,
        Model model
) {
    List<Kos> allKos = kosService.getAllKos();

    // Filter tipe
    if (typeFilter != null && !typeFilter.isEmpty()) {
        allKos = allKos.stream()
                .filter(k -> typeFilter.equalsIgnoreCase(k.getCategory()))
                .collect(Collectors.toList());
    }

    // Filter harga
    if (priceFilter != null && !priceFilter.isEmpty()) {
        switch (priceFilter) {
            case "below500":
                allKos = allKos.stream()
                        .filter(k -> k.getHarga() < 500000)
                        .collect(Collectors.toList());
                break;
            case "range500to1m":
                allKos = allKos.stream()
                        .filter(k -> k.getHarga() >= 500000 && k.getHarga() <= 1000000)
                        .collect(Collectors.toList());
                break;
            case "above1m":
                allKos = allKos.stream()
                        .filter(k -> k.getHarga() > 1000000)
                        .collect(Collectors.toList());
                break;
        }
    }

    // Kirim data ke view
    model.addAttribute("listkos", allKos);
    model.addAttribute("priceFilter", priceFilter);
    model.addAttribute("typeFilter", typeFilter);

    return "user/rooms";
}


    @GetMapping("/about")
    public String showAbout() {
        return "user/about";
    }
    
}

// @GetMapping("/")
// public String showIndex() {
//     return "user/index";
// }

// @GetMapping("/login")
// public String getLogin() {
// return "login";
// }
