<<<<<<< HEAD
package com.example.crud.controller;
=======
package com.example.kos.controller;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
import com.example.crud.models.Kos;
import com.example.crud.service.KosService;
=======
import com.example.kos.models.Kos;
import com.example.kos.service.KosService;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

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
