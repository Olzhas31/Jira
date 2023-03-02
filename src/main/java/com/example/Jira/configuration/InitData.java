package com.example.Jira.configuration;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitData implements CommandLineRunner {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
    }

    private void initAdmin() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("100"))
                .role(Roles.ADMIN.name())
                .enabled(true)
                .locked(false)
                .build();
        if (!userRepository.existsByUsername(admin.getUsername())) {
            userRepository.save(admin);
        }
    }
}
