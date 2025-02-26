package com.whitespace.bankapi.repository;

import com.whitespace.bankapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>/*, JpaSpecificationExecutor<Employee>*/ {
    Optional<Employee> findByUsername(String username);
}
