package com.whitespace.bankapi.initializer;

import com.whitespace.bankapi.model.Employee;
import com.whitespace.bankapi.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EmployeeDataInitializer {

    @Bean
    public CommandLineRunner createSuperUser(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (employeeRepository.findByUsername("admin").isEmpty()) {
                Employee admin = new Employee();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpassword")); // Change default password as needed
                admin.setSuperUser(true);
                // Assign a global role for superuser; Spring Security expects roles to be prefixed with "ROLE_"
                admin.getRoles().add("ROLE_SUPERUSER");
                employeeRepository.save(admin);
                System.out.println("Super user created: admin/adminpassword\n\n");
            }
        };
    }

    @Bean
    public CommandLineRunner createRegularUser(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (employeeRepository.findByUsername("user").isEmpty()) {
                Employee admin = new Employee();
                admin.setUsername("user");
                admin.setPassword(passwordEncoder.encode("password")); // Change default password as needed
                admin.setSuperUser(false);
                // Assign a global role for superuser; Spring Security expects roles to be prefixed with "ROLE_"
                admin.getRoles().add("ROLE_USER");
                employeeRepository.save(admin);
                System.out.println("Regular user created: user/password\n\n");
            }
        };
    }
}
