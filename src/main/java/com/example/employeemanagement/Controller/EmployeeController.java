package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee,
                                   @RequestParam(required = false) Long departmentId,
                                   @RequestParam(required = false) Long managerId) {
        return employeeService.createEmployee(employee, departmentId, managerId);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee employee,
                                   @RequestParam(required = false) Long departmentId,
                                   @RequestParam(required = false) Long managerId) {
        return employeeService.updateEmployee(id, employee, departmentId, managerId);
    }

    @GetMapping
    public Object listEmployees(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                @RequestParam(required = false) Boolean lookup) {
        if (lookup != null && lookup) {
            return employeeService.lookupEmployeeNamesAndIds();
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> employeePage = employeeService.listEmployees(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("employees", employeePage.getContent());
            response.put("currentPage", employeePage.getNumber());
            response.put("totalItems", employeePage.getTotalElements());
            response.put("totalPages", employeePage.getTotalPages());
            return response;
        }
    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }


}