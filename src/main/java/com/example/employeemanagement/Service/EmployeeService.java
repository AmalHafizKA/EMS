package com.example.employeemanagement.Service;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.Repository.DepartmentRepository;
import com.example.employeemanagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Employee createEmployee(Employee employee, Long departmentId, Long managerId) {
        Employee existingEmployee = employeeRepository.findByNameAndDateOfBirth(employee.getName(), employee.getDateOfBirth());
        if (existingEmployee != null) {
            throw new IllegalArgumentException("Employee with the same details already exists");
        }

        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with id : " + departmentId));
            employee.setDepartment(department);
        }

        if (managerId != null) {
            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with id : " + managerId));
            employee.setManager(manager);
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee, Long departmentId, Long managerId) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id : " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setRole(updatedEmployee.getRole());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());
        existingEmployee.setYearlyBonusPercentage(updatedEmployee.getYearlyBonusPercentage());

        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with id : " + departmentId));
            existingEmployee.setDepartment(department);
        }

        if (managerId != null) {
            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with id : " + managerId));
            existingEmployee.setManager(manager);
        }

        return employeeRepository.save(existingEmployee);
    }

    public Page<Employee> listEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }



    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Map<String, Object>> lookupEmployeeNamesAndIds() {
        return employeeRepository.findAllNamesAndIds();

    }
}