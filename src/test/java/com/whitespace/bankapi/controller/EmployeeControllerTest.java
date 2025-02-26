package com.whitespace.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitespace.bankapi.SecurityConfig;
import com.whitespace.bankapi.dto.CreateEmployeeRequest;
import com.whitespace.bankapi.dto.UpdateEmployeeRequest;
import com.whitespace.bankapi.initializer.CustomerDataInitializer;
import com.whitespace.bankapi.initializer.EmployeeDataInitializer;
import com.whitespace.bankapi.model.Employee;
import com.whitespace.bankapi.repository.CustomerRepository;
import com.whitespace.bankapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({SecurityConfig.class, EmployeeDataInitializer.class, CustomerDataInitializer.class})
//@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "adminpassword";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //    @MockBean
    @Autowired
    private EmployeeRepository employeeRepository;

    //    @MockBean
    @Autowired
    private CustomerRepository customerRepository;


    //    @MockBean
    @Autowired
    private UserDetailsService employeeDetailsService;

    //    @MockBean
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateEmployee_Success() throws Exception {
//        Employee employee = new Employee();
//        employee.setUsername("testuser");
//        employee.setPassword("testpassword");
//        employee.setSuperUser(false);

        // Ensure no duplicate exists.
//        when(employeeRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        // Simulate password encoding.
//        when(passwordEncoder.encode("testpassword")).thenReturn("encodedPassword");
        // Simulate repository save.
//        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        var request = new CreateEmployeeRequest(
                "testuser",
                "testpassword"
        );

        mockMvc.perform(post("/api/employees")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setUsername("user1");
        employee.setPassword("pass");
        employee.setSuperUser(false);

//        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/api/employees")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
//        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/1134536124567")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateEmployee_NotFound() throws Exception {
        var request = new UpdateEmployeeRequest("newuser","newpass");

//        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/employees/18383894004")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request
                        )))

                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEmployee_NotFound() throws Exception {
//        when(employeeRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/employees/18391049949")
                        .with(httpBasic(USERNAME, PASSWORD))
                )
                .andExpect(status().isNotFound());
    }
}
