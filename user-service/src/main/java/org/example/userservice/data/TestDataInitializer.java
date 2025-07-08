package org.example.userservice.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.userservice.model.Role;
import org.example.userservice.model.RoleType;
import org.example.userservice.model.User;
import org.example.userservice.repository.RoleRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_ADMIN)));

            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_USER)));
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminPass"));
            admin.setEmail("admin@gmail.com");
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("userPass"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }
}
