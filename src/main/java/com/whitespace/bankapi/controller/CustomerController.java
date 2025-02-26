package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "Create a new customer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "409", description = "Customer already exists")
            })
    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // Assuming customer.id is provided (or you could let JPA generate it)
        if (customer.getId() != null && customerRepository.existsById(customer.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Get all customers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of customers",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
            })
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Get a customer by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        return customerOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Update an existing customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Customer customer = customerOpt.get();
        customer.setName(updatedCustomer.getName());
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Delete a customer",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Customer deleted"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
