<<<<<<< HEAD
package com.example.crud.controller;

=======
package com.example.kos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
import com.example.crud.models.User;
=======
import com.example.kos.models.Kos;
import com.example.kos.models.User;
import com.example.kos.repository.KosRepository;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

<<<<<<< HEAD
=======
    @Autowired
    private KosRepository kosRepository;

>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

<<<<<<< HEAD
        String username = user.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);
        return "admin/index"; // template admin/dashboard.html
    }

    // @GetMapping("/login")
    // public String getLogin() {
    // return "login";
    // }
=======
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
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

}
