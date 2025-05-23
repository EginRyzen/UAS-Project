<<<<<<< HEAD
package com.example.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.DeleteMapping;
=======
package com.example.kos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

<<<<<<< HEAD
import com.example.crud.models.User;
// import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;
=======
import com.example.kos.models.User;
// import com.example.kos.repository.UserRepository;
import com.example.kos.service.UserService;
>>>>>>> 16a4b6aa770b4a9767edf8d4d74430fd68cb4c7a

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {
    // private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserService userService) {
        // this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showIndex(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        String username = user.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        model.addAttribute("listuser", userService.getAllUser());
        return "admin/tableuser";
    }

    @PostMapping("/create/user")
    public String createUser(@ModelAttribute User user, HttpSession session, Model model) {
        User isuser = (User) session.getAttribute("loggedInUser");

        if (isuser == null) {
            return "redirect:/auth/login";
        }

        String username = isuser.getUsername(); // ✅ Ambil username
        model.addAttribute("username", username);

        userService.save(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/edit/user")
    public String editUser(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("username", loggedInUser.getUsername());

        userService.update(user); // Pastikan update logic ada di service
        return "redirect:/admin/user"; // redirect kembali ke daftar user
    }

    @PostMapping("/user/status/{id}")
    public String toggleUserStatus(@PathVariable Integer id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null && loggedInUser.getId() == id) {
            redirectAttributes.addFlashAttribute("error", "Tidak bisa ubah status user yang sedang login!!.");
            return "redirect:/admin/user";
        }

        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User tidak ditemukan!!.");
            return "redirect:/admin/user";
        }

        // Cek jumlah user ACTIVE
        long activeCount = userService.countByStatus(User.Status.ACTIVE);

        if (user.getStatus() == User.Status.ACTIVE && activeCount <= 1) {
            redirectAttributes.addFlashAttribute("error", "Tidak bisa menonaktifkan user terakhir yang aktif!!!.");
            return "redirect:/admin/user";
        }

        // Toggle status
        if (user.getStatus() == User.Status.ACTIVE) {
            user.setStatus(User.Status.INACTIVE);
        } else {
            user.setStatus(User.Status.ACTIVE);
        }

        userService.saveWithoutPasswordEncode(user); // buat method ini agar tidak reset password
        redirectAttributes.addFlashAttribute("success", "Status user berhasil diubah.");
        return "redirect:/admin/user";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null && loggedInUser.getId() == id) {
            redirectAttributes.addFlashAttribute("error",
                    "Tidak dapat menghapus akun Anda sendiri karena sedang login.");
            return "redirect:/admin/user";
        }

        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User tidak ditemukan!!.");
            return "redirect:/admin/user";
        }

        long totalUsers = userService.countUsers();
        if (totalUsers <= 1) {
            redirectAttributes.addFlashAttribute("error", "Tidak dapat menghapus karena hanya ada 1 user.");
            return "redirect:/admin/user";
        }

        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "User berhasil dihapus.");
        return "redirect:/admin/user";
    }

}
