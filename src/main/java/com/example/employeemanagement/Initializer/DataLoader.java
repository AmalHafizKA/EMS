package com.example.employeemanagement.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.employeemanagement.Repository.EmployeeRepository;
import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.Repository.DepartmentRepository;
import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (departmentRepository.count() == 0 && employeeRepository.count() == 0) {

            Department dept1 = createDepartment("HR");
            Department dept2 = createDepartment("Finance");
            Department dept3 = createDepartment("IT");

            // Save departments before assigning them to employees
            departmentRepository.save(dept1);
            departmentRepository.save(dept2);
            departmentRepository.save(dept3);

            // Creating department heads
            Employee dept1Head = createEmployee("Arun", LocalDate.of(1980, 1, 1), 80000.0, "ABC Address", "HR Department Head", LocalDate.now().minusYears(5), 10.0);
            Employee dept2Head = createEmployee("Rani", LocalDate.of(1985, 6, 15), 85000.0, "EFG Address", "Finance Department Head", LocalDate.now().minusYears(4), 12.0);
            Employee dept3Head = createEmployee("Manuel", LocalDate.of(1992, 3, 20), 90000.0, "XYZ Address", "IT Department Head", LocalDate.now().minusYears(3), 15.0);

            // Assign departments to department heads
            dept1Head.setDepartment(dept1);
            dept2Head.setDepartment(dept2);
            dept3Head.setDepartment(dept3);

            // Save department heads
            employeeRepository.save(dept1Head);
            employeeRepository.save(dept2Head);
            employeeRepository.save(dept3Head);

            // Associate department heads with departments
            dept1.setHead(dept1Head);
            dept2.setHead(dept2Head);
            dept3.setHead(dept3Head);

            departmentRepository.save(dept1);
            departmentRepository.save(dept2);
            departmentRepository.save(dept3);

            // Creating employees
            createEmployeesForDepartment(dept1, dept1Head);
            createEmployeesForDepartment(dept2, dept2Head);
            createEmployeesForDepartment(dept3, dept3Head);
        }
    }

    private Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        department.setCreationDate(LocalDate.now());
        return department;
    }

    private void createEmployeesForDepartment(Department department,Employee departmentHead) {
        Employee firstEmployee = null;
        for (int i = 1; i <= 9; i++) { // Assuming each department has 9 employees (excluding the head)
            Employee employee = createEmployee("Employee " + i, LocalDate.of(1990, 1, 1).plusDays(i), 50000.0 + i * 1000, "Address " + i, "Role " + i, LocalDate.now().minusYears(1).plusDays(i), 5.0 + i);
            employee.setDepartment(department);

            if (i == 1) {
                employee.setManager(null);
                firstEmployee = employee;
            } else {
                // Assign the first employee as the manager for all other employees in the department
                employee.setManager(firstEmployee);
            }

            employeeRepository.save(employee);
        }
    }

    private Employee createEmployee(String name, LocalDate dateOfBirth, Double salary, String address, String role, LocalDate joiningDate, Double yearlyBonusPercentage) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setDateOfBirth(dateOfBirth);
        employee.setSalary(salary);
        employee.setAddress(address);
        employee.setRole(role);
        employee.setJoiningDate(joiningDate);
        employee.setYearlyBonusPercentage(yearlyBonusPercentage);
        return employee;
    }
}