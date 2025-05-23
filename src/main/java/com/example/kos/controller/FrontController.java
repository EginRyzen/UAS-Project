<<<<<<< HEAD
package com.example.crud.controller;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

=======
package com.example.kos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
import com.example.crud.models.Kos;
import com.example.crud.service.KosService;
=======
import com.example.kos.models.Kos;
import com.example.kos.service.KosService;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

@Controller
public class FrontController {

    private final KosService kosService;

    // @Autowired
<<<<<<< HEAD

=======
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
    public FrontController(KosService kosService) {
        this.kosService = kosService;
    }

<<<<<<< HEAD

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


=======
    // Versi tanpa filter, dengan pagination manual
    @GetMapping("/rooms")
    public String getAllKos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {

        List<Kos> allKos = kosService.getAllKos();

        // Manual pagination
        int start = page * size;
        int end = Math.min(start + size, allKos.size());

        List<Kos> pagedList = new ArrayList<>();
        if (start <= end) {
            pagedList = allKos.subList(start, end);
        }

        int totalPages = (int) Math.ceil((double) allKos.size() / size);

        model.addAttribute("listkos", pagedList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "user/rooms";
    }

    // Versi dengan filter + pagination manual
    @GetMapping(value = "/rooms", params = { "priceFilter", "typeFilter" })
    public String filterKos(
            @RequestParam(required = false) String priceFilter,
            @RequestParam(required = false) String typeFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {
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

        // Manual pagination
        int start = page * size;
        int end = Math.min(start + size, allKos.size());

        List<Kos> pagedList = new ArrayList<>();
        if (start <= end) {
            pagedList = allKos.subList(start, end);
        }

        int totalPages = (int) Math.ceil((double) allKos.size() / size);

        // Kirim data ke view
        model.addAttribute("listkos", pagedList);
        model.addAttribute("priceFilter", priceFilter);
        model.addAttribute("typeFilter", typeFilter);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "user/rooms";
    }

>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
    @GetMapping("/about")
    public String showAbout() {
        return "user/about";
    }
<<<<<<< HEAD
    
}

// @GetMapping("/")
// public String showIndex() {
//     return "user/index";
// }

// @GetMapping("/login")
// public String getLogin() {
// return "login";
// }
=======
}
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
