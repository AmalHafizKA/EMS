package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public Department createDepartment(@RequestBody Department department,
                                       @RequestParam(required = false) Long headId) {
        return departmentService.createDepartment(department, headId);
    }


    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id,@RequestParam(required = false)Long headId ,@RequestBody Department department) {
        return departmentService.updateDepartment(id,headId, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping
    public Object getDepartments(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(required = false) String expand,
                                 @RequestParam(required = false) Long id) {
        if (id != null) {
            if ("employee".equals(expand)) {
                return departmentService.getDepartmentWithEmployees(id);
            } else {
                return departmentService.getDepartmentWithoutEmployees(id);
            }
        } else {
            if ("employee".equals(expand)) {
                List<Department> departments = departmentService.listDepartmentsWithEmployees();
                Map<String, Object> response = new HashMap<>();
                response.put("departments", departments);
                return response;
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<Department> departmentPage = departmentService.listDepartments(pageable);
                Map<String, Object> response = new HashMap<>();
                response.put("departments", departmentPage.getContent());
                response.put("currentPage", departmentPage.getNumber());
                response.put("totalItems", departmentPage.getTotalElements());
                response.put("totalPages", departmentPage.getTotalPages());
                return response;
            }
        }
    }


}
