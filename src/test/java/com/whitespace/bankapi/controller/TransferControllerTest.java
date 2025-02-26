package com.whitespace.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitespace.bankapi.SecurityConfig;
import com.whitespace.bankapi.dto.TransferRequest;
import com.whitespace.bankapi.initializer.CustomerDataInitializer;
import com.whitespace.bankapi.initializer.EmployeeDataInitializer;
import com.whitespace.bankapi.repository.CustomerRepository;
import com.whitespace.bankapi.repository.EmployeeRepository;
import com.whitespace.bankapi.repository.TransferRepository;
import com.whitespace.bankapi.service.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Order;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({SecurityConfig.class, EmployeeDataInitializer.class, CustomerDataInitializer.class})
//@WebMvcTest(TransferController.class)
public class TransferControllerTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    //    @MockBean
    @Autowired
    private UserDetailsService employeeDetailsService;

    //    @MockBean
    @Autowired
    private EmployeeRepository employeeRepository;

    //    @MockBean
    @Autowired
    private CustomerRepository customerRepository;

    //    @MockBean
    @Autowired
    private TransferService transferService;

    //    @MockBean
    @Autowired
    private TransferRepository transferRepository;

//    @Test
//    @Order(5)
//    public void testTransferMoney() throws Exception {
//
//        System.out.println("Running testTransferMoney");
//        var request = new TransferRequest(
//                1L, 2L, 500L
//        );
//        var body = objectMapper.writeValueAsString(request);
//        System.out.println("Initialized Request testTransferMoney: " + body + "\n\n");
//
//        // Stub the service call. If no exception is thrown, the transfer is considered successful.
////        doNothing().when(transferService).transferMoney(
////                ArgumentMatchers.eq(1L),
////                ArgumentMatchers.eq(2L),
////                ArgumentMatchers.eq(300L)
////        );
//
//        mockMvc.perform(post("/api/transfers")
//                        .with(httpBasic(USERNAME, PASSWORD))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Transfer successful"));
//
//        System.out.println("Done with Request testTransferMoney");
//    }
}
