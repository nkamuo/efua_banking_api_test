package com.whitespace.bankapi;
import com.whitespace.bankapi.repository.EmployeeRepository;
import com.whitespace.bankapi.security.EmployeeDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    @Autowired
//    private  EmployeeRepository employeeRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Primary
//    @Bean
//    protected EmployeeDetailsService getEmployeeDetailsService(EmployeeRepository employeeRepository){
//        return new EmployeeDetailsServiceImpl(employeeRepository);
//    }

    // Expose your custom UserDetailsService as a bean
    @Primary
    @Bean
    public UserDetailsService EmployeeDetailsServiceImpl(EmployeeRepository employeeRepository) {
        return new EmployeeDetailsServiceImpl(employeeRepository);
//        return employeeDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/employees")
                            .hasRole("SUPERUSER")
                    ;
                })
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .hasRole("ADMIN")// Require authentication for all requests
                )
                .httpBasic(basic -> {}); // Enable HTTP Basic Authentication

//        http .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().permitAll()
//                    ;
//                });

        // Disable CSRF (if needed, e.g., for stateless APIs)
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
}
