package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.EmployeePatchDTO;
import com.example.api.dto.EmployeeUpdateDTO;
import com.example.api.exception.EmailAlreadyUsedException;
import com.example.api.exception.EmployeeNotFoundException;
import com.example.api.model.Employee;
import com.example.api.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee getEmployee(final Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> EmployeeNotFoundException.forId(id));
    }

    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee() {
        employeeRepository.deleteAll();
    }

    public void deleteEmployeeById(final Long id) {
        if (!employeeRepository.existsById(id)) {
            throw EmployeeNotFoundException.forId(id);
        }

        employeeRepository.deleteById(id);
    }

    public Employee saveEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailAlreadyUsedException(employee.getEmail());
        }

        if (employee.getRole() == null || employee.getRole().isBlank()) {
            employee.setRole("User");
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(final Long id, EmployeeUpdateDTO dto) {
        Employee employee = getEmployee(id);

        if (!employee.getEmail().equalsIgnoreCase(dto.getEmail())
                && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException(dto.getEmail());
        }

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return employeeRepository.save(employee);
    }

    public Employee partialUpdate(final Long id, EmployeePatchDTO dto) {
        Employee employee = getEmployee(id);

        if (dto.getFirstName() != null) {
            employee.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            employee.setLastName(dto.getLastName());
        }

        if (dto.getEmail() != null) {
            if (!employee.getEmail().equalsIgnoreCase(dto.getEmail())
                    && employeeRepository.existsByEmail(dto.getEmail())) {
                throw new EmailAlreadyUsedException(dto.getEmail());
            }

            employee.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return employeeRepository.save(employee);
    }
}