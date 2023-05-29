package org.example.service;

import org.example.model.Employee;

import java.util.List;

public interface EmployeeManager {

    // tworzenie pracowanika
    int createEmployee(Employee employee);

    // pobranie wszystkich pracowników
    List<Employee> getAllEmployees();

    // pobranie pracownika po id
    Employee getEmployeeById(int id);

    // aktualizacja pracownika
    void updateEmployee(Employee employee);

    // usunięcie pracownika
    void deleteEmployee(int id);

}
