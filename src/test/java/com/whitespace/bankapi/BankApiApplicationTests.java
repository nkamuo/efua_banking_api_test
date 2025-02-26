package com.whitespace.bankapi;

import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.repository.EmployeeRepository;
import com.whitespace.bankapi.repository.TransferRepository;
import com.whitespace.bankapi.security.EmployeeDetailsServiceImpl;
import com.whitespace.bankapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableJpaRepositories()
//@Import({SecurityConfig.class, EmployeeDetailsServiceImpl.class, EmployeeRepository.class})
@Import(BankApiApplication.class)
@SpringBootTest()
//@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
class BankApiApplicationTests {

//	@MockBean
//	private EmployeeRepository employeeRepository;

//	@MockBean
//	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

}
