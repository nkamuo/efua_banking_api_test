package com.whitespace.bankapi.initializer;

import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CustomerDataInitializer {

    @Bean
    public CommandLineRunner loadCustomers(CustomerRepository customerRepository) {
        return args -> {
            // Check if the customer table is empty
            if (customerRepository.count() == 0) {
                Customer[] customers = {
                    new Customer(1L, "Arisha Barron"),
                    new Customer(2L, "Branden Gibson"),
                    new Customer(3L, "Rhonda Church"),
                    new Customer(4L, "Georgina Hazel")
                };
                customerRepository.saveAll(Arrays.asList(customers));
                System.out.println("Customer data loaded.");
            } else {
                System.out.println("Customer data already exists. Skipping initialization.");
            }
        };
    }




}
