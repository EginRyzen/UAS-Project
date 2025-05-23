package com.example.kos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.kos.models.User;
import com.example.kos.repository.UserRepository;
// import com.example.crud.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Username atau Password salah");
            return "auth/login";
        }

        if (user.getStatus() == User.Status.INACTIVE) {
            model.addAttribute("error", "Akun tidak aktif");
            return "auth/login";
        }

        // Simpan data user ke session
        session.setAttribute("loggedInUser", user);

        org.springframework.security.core.userdetails.User authUser = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                java.util.List.of(() -> "ROLE_USER"));

        org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                authUser, null, authUser.getAuthorities());

        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Hapus session
        session.invalidate();

        // Hapus authentication dari SecurityContext
        SecurityContextHolder.clearContext();

        // Redirect ke halaman login
        return "redirect:/auth/login";
    }

}
