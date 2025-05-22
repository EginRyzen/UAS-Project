package com.example.kos.seeders;

import com.example.kos.models.User;
import com.example.kos.models.User.Status;
import com.example.kos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("admin");
                user.setEmail("admin@example.com");
                user.setPassword(passwordEncoder.encode("12345678"));
                user.setName("Admin");
                user.setStatus(Status.ACTIVE);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                userRepository.save(user);

                System.out.println("✅ Admin user berhasil dibuat: admin@example.com / admin123");
            } else {
                System.out.println("ℹ️ Data user sudah tersedia, tidak membuat user baru.");
            }
        };
    }
}
