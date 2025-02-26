package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.dto.CreateEmployeeRequest;
import com.whitespace.bankapi.dto.UpdateEmployeeRequest;
import com.whitespace.bankapi.model.Employee;
import com.whitespace.bankapi.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE: Endpoint to create a new employee

    @Operation(summary = "Create a new employee", responses = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "409", description = "Employee already exists")
    })
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest requst) {
        if (employeeRepository.findByUsername(requst.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        var employee = new Employee();
        employee.setUsername(requst.username());
        employee.setPassword(passwordEncoder.encode(requst.password()));
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ: Endpoint to list all employees
    @Operation(summary = "Retrieve all employees", responses = {
            @ApiResponse(responseCode = "200", description = "List of employees",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class))))
    })
    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        if (perPage > 100){
            perPage = 100;
        }
        if(page > 0){
            page--;
        }
        var employees = employeeRepository.findAll(Pageable.ofSize(perPage).withPage(page));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // READ: Endpoint to get an employee by id
    @Operation(summary = "Retrieve an employee by id", responses = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        return employeeOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // UPDATE: Endpoint to update an employee
    @Operation(summary = "Update an existing employee", responses = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeRequest request) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var employee = optionalEmployee.get();

        Optional<Employee> existingEmployee = employeeRepository.findByUsername(request.username());
        if (existingEmployee.isPresent()) {
            var _employee = existingEmployee.get();
            if(!_employee.equals(employee)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }


        // Update fields; note: you might want to validate the updated username if necessary.
        employee.setUsername(request.username());

        // If a new password is provided, update it after encoding.
        if (employee.getPassword() != null && !request.password().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(request.password()));
        }
        // Update additional fields
//        employee.setRoles(request.roles());

        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.ok(saved);
    }

    // DELETE: Endpoint to delete an employee by id
    @Operation(summary = "Delete an employee", responses = {
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var employee = optionalEmployee.get();
        if(employee.isSuperUser()){
//            You cannot delete a super admin
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
