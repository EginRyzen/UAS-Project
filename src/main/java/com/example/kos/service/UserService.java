package com.example.kos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.kos.models.User;
import com.example.kos.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void save(User user) {
        if (user.getStatus() == null) {
            user.setStatus(User.Status.ACTIVE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void update(User user) {
        // Ambil user dari database berdasarkan ID
        Optional<User> existingUserOpt = userRepository.findById(user.getId());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update field-field yang ingin diubah
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setUpdatedAt(LocalDateTime.now());

            // Jika password diizinkan diubah (opsional)
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // atau hash jika pakai security
            }

            // Simpan kembali ke DB
            userRepository.save(existingUser);
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public long countByStatus(User.Status status) {
        return userRepository.countByStatus(status);
    }

    // Jika ingin update tanpa menyentuh password
    public void saveWithoutPasswordEncode(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

}
