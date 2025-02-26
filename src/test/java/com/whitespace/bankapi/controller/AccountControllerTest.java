package com.whitespace.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitespace.bankapi.SecurityConfig;
import com.whitespace.bankapi.dto.CreateAccountRequest;
import com.whitespace.bankapi.dto.TransferRequest;
import com.whitespace.bankapi.initializer.CustomerDataInitializer;
import com.whitespace.bankapi.initializer.EmployeeDataInitializer;
import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.repository.CustomerRepository;
import com.whitespace.bankapi.repository.EmployeeRepository;
import com.whitespace.bankapi.service.AccountService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
//import org.junit.jupiter.api.Order;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({SecurityConfig.class, EmployeeDataInitializer.class, CustomerDataInitializer.class})
public class AccountControllerTest {


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
    private AccountService accountService;

//    @MockBean
    @Autowired
    private CustomerRepository customerRepository;

//    @MockBean
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Order(1)
    public void testCreateFirstAccount() throws Exception {
        // Create a sample request
        CreateAccountRequest request = new CreateAccountRequest(
                1L,
                1500L
        );
        // Create a dummy Account to be returned by the service
//        Account account = new Account(1L);
//        account.setBalance(1000L);
        // Assume the account's customer field is set internally

//        when(accountService.createAccount(ArgumentMatchers.eq(1L), ArgumentMatchers.eq(1000L)))
//                .thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(1500L));
    }

    @Test
    @Order(0)
    public void testCreate2ndAccount() throws Exception {
        System.out.println("Running testCreateSecondAccount");
        // Create a sample request
        var request = new CreateAccountRequest(
                2L,
                100L
        );
        mockMvc.perform(post("/api/accounts")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.balance").value(100L));
    }


    @Test
    @Order(3)
    public void testInitialGetBalance() throws Exception {
        Long accountId = 1L;
        Long balance = 1500L;
        doTestGetBalance(accountId,balance);
    }

    @Test
    @Order(5)
    public void testTransferMoney() throws Exception {

        System.out.println("Running testTransferMoney");
        var request = new TransferRequest(
                1L, 2L, 500L
        );
        var body = objectMapper.writeValueAsString(request);
        System.out.println("Initialized Request testTransferMoney: " + body + "\n\n");

        // Stub the service call. If no exception is thrown, the transfer is considered successful.
//        doNothing().when(transferService).transferMoney(
//                ArgumentMatchers.eq(1L),
//                ArgumentMatchers.eq(2L),
//                ArgumentMatchers.eq(300L)
//        );

        mockMvc.perform(post("/api/transfers")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful"));

        System.out.println("Done with Request testTransferMoney");
    }


    @Test
    @Order(10)
    public void testZGetTransferHistory() throws Exception {
        Long accountId = 1L;
//        Transfer transfer = new Transfer(accountId, 2L, 200L);
//        List<Transfer> transfers = Collections.singletonList(transfer);
//        when(accountService.getTransferHistory(accountId)).thenReturn(transfers);

        mockMvc.perform(get("/api/accounts/{accountId}/transfers", accountId)
                        .with(httpBasic(USERNAME, PASSWORD))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].sourceAccountId").value(accountId))
                .andExpect(jsonPath("$.content[0].destinationAccountId").value(2))
                .andExpect(jsonPath("$.content[0].amount").value(500L));
    }

    @Test
    @Order(10)
    public void testZFinalGetBalance() throws Exception {
        Long accountId = 1L;
        Long balance = 1000L;
        doTestGetBalance(accountId,balance);
    }


    public void doTestGetBalance( Long accountId,
    Long balance) throws Exception {
//        Long accountId = 1L;
//        Long balance = 1500L;
//        when(accountService.getBalance(accountId)).thenReturn(balance);

        mockMvc.perform(
                        get("/api/accounts/{accountId}/balance", accountId)
                                .with(httpBasic(USERNAME, PASSWORD))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(balance.toString()));
    }
}
