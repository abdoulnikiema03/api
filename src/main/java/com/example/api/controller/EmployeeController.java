package com.example.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.EmployeePatchDTO;
import com.example.api.dto.EmployeeRequestDTO;
import com.example.api.dto.EmployeeResponseDTO;
import com.example.api.dto.EmployeeUpdateDTO;
import com.example.api.mapper.EmployeeMapper;
import com.example.api.model.Employee;
import com.example.api.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO dto) {

        Employee employee = EmployeeMapper.toEntity(dto);
        Employee savedEmployee = employeeService.saveEmployee(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EmployeeMapper.toResponseDTO(savedEmployee));
    }

    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getEmployees() {
        List<EmployeeResponseDTO> result = new ArrayList<>();

        employeeService.getEmployees()
                .forEach(employee -> result.add(EmployeeMapper.toResponseDTO(employee)));

        return result;
    }

    @GetMapping("/employee/{id}")
    public EmployeeResponseDTO getEmployee(@PathVariable Long id) {
        return EmployeeMapper.toResponseDTO(employeeService.getEmployee(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/employees")
    public ResponseEntity<Void> deleteEmployees() {
        employeeService.deleteEmployee();
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/employee/{id}")
    public EmployeeResponseDTO updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDTO dto) {

        return EmployeeMapper.toResponseDTO(employeeService.updateEmployee(id, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/employee/{id}")
    public EmployeeResponseDTO partialUpdate(
            @PathVariable Long id,
            @RequestBody EmployeePatchDTO dto) {

        return EmployeeMapper.toResponseDTO(employeeService.partialUpdate(id, dto));
    }
}