package com.example.crud.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crud.dtos.KosDTO;
import com.example.crud.models.Kos;
import com.example.crud.models.User;
import com.example.crud.repository.KosRepository;
import com.example.crud.service.KosService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class KosController {
    private final KosRepository kosRepository;
    private final KosService kosService;

    // Dapat dihilangkan di Spring modern
    public KosController(KosRepository kosRepository, KosService kosService) {
        this.kosRepository = kosRepository;
        this.kosService = kosService;
    }

    @GetMapping("/kos")
    public String getAllKos(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        String username = user.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        model.addAttribute("listkos", kosService.getAllKos());
        return "admin/tablekos";
    }

    @GetMapping("/kos/add")
    public String showAddKos(Model model, HttpSession session) {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        model.addAttribute("kos", new Kos());
        return "admin/forminsert";
    }

    @PostMapping("/kos/add")
    public String addKos(@ModelAttribute KosDTO kosDTO, HttpSession session, Model model) throws Exception {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        Kos kos = new Kos();

        kos.setNamaKos(kosDTO.getNamaKos());
        kos.setHarga(kosDTO.getHarga());
        kos.setCategory(kosDTO.getCategory());
        kos.setLokasi(kosDTO.getLokasi());
        kos.setKontak(kosDTO.getKontak());
        kos.setDeskripsi(kosDTO.getDeskripsi());
        kos.setCreatedAt(LocalDateTime.now());
        kos.setUpdatedAt(LocalDateTime.now());

        MultipartFile image = kosDTO.getImage();

        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagekos/";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            image.transferTo(new File(uploadDir + fileName));

            kos.setImage(fileName); // hanya nama file disimpan di DB
        }

        kosService.addKos(kos);
        return "redirect:/admin/kos";
    }

    @Transactional
    @PostMapping("/kos/delete/{id}")
    public String deleteKos(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, HttpSession session,
            Model model) {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        Kos kos = kosRepository.findById(id).orElse(null);
        if (kos == null) {
            return "redirect:/admin/kos";
        }

        // Hapus gambar terlebih dahulu
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagekos/";
        String filePath = uploadDir + kos.getImage();

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        kosRepository.deleteById(id);
        // redirectAttributes.addFlashAttribute("message", "Data berhasil dihapus");
        return "redirect:/admin/kos";
    }

    @GetMapping("/kos/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        Optional<Kos> optionalKos = kosService.getKosById(id);
        if (optionalKos.isPresent()) {
            Kos kos = optionalKos.get();
            model.addAttribute("kos", kos);
            return "admin/formupdate";
        } else {
            return "redirect:/admin/kos";
        }
    }

    @PostMapping("/kos/edit/{id}")
    public String updateKos(@PathVariable Long id, @ModelAttribute KosDTO kosDTO, HttpSession session, Model model)
            throws Exception {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        Kos kos = kosRepository.findById(id).orElseThrow();

        kos.setNamaKos(kosDTO.getNamaKos());
        kos.setHarga(kosDTO.getHarga());
        kos.setLokasi(kosDTO.getLokasi());
        kos.setCategory(kosDTO.getCategory());
        kos.setKontak(kosDTO.getKontak());
        kos.setDeskripsi(kosDTO.getDeskripsi());
        kos.setUpdatedAt(LocalDateTime.now());

        MultipartFile image = kosDTO.getImage();
        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagekos/";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            // Hapus file lama
            String oldImageName = kos.getImage(); // ambil nama file lama dari DB
            File oldImageFile = new File(uploadDir + oldImageName);
            if (oldImageFile.exists()) {
                oldImageFile.delete(); // hapus file lama
            }

            // Upload file baru
            image.transferTo(new File(uploadDir + fileName));
            kos.setImage(fileName); // simpan nama file baru ke DB
        }

        kosService.updateKos(kos); // buat method update di service kalau belum ada
        return "redirect:/admin/kos";
    }

    @GetMapping("/kos/detail/{id}")
    public String getKosById(@PathVariable Long id, Model model, HttpSession session) {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        Optional<Kos> kos = kosService.getKosById(id);
        if (kos.isPresent()) {
            model.addAttribute("kos", kos.get());
            return "admin/detailkos";
        } else {
            return "admin/index";
        }
    }

}
