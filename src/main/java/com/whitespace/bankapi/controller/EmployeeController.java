package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.model.Employee;
import com.whitespace.bankapi.repository.EmployeeRepository;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint for a super user to create new employees.
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        // In a real application, validate that the current user is a super user.
        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        // You can assign default roles or let the request specify them
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
