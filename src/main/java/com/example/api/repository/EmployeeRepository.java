package com.example.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.example.api.model.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    
    boolean existsByEmail(String email);
}
