package com.example.employeemanagement.Service;

import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        // Update fields
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setRole(updatedEmployee.getRole());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());
        existingEmployee.setYearlyBonusPercentage(updatedEmployee.getYearlyBonusPercentage());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setManager(updatedEmployee.getManager());
        return employeeRepository.save(existingEmployee);
    }

    public Page<Employee> listEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Map<String, Object>> lookupEmployeeNamesAndIds() {
        return employeeRepository.findAllNamesAndIds();

    }
}