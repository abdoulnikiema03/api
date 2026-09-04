package com.example.api.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public static EmployeeNotFoundException forId(Long id) {
        return new EmployeeNotFoundException("Aucun employé trouvé avec l'id " + id);
    }
    
}
