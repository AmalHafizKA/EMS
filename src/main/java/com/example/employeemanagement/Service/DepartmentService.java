package com.example.employeemanagement.Service;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.Repository.DepartmentRepository;
import com.example.employeemanagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Department createDepartment(Department department, Long headId) {
        if (headId != null) {
            Employee head = employeeRepository.findById(headId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found with id : " + headId));
            department.setHead(head);
        }
        return departmentRepository.save(department);
    }


    public Department updateDepartment(Long id, Long headId, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id : " + id));

        if (headId != null) {
            Employee head = employeeRepository.findById(headId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found with id : " + headId));
            existingDepartment.setHead(head);
        }
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setCreationDate(updatedDepartment.getCreationDate());

        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Department not found with id : " + id));

        if (!department.getEmployees().isEmpty()) {
            throw new IllegalStateException("Cannot delete department with assigned employees");
        }

        departmentRepository.deleteById(id);
    }


    public Page<Department> listDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public List<Department> listDepartmentsWithEmployees() {
        return departmentRepository.findAllWithEmployees();
    }

    public Department getDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id).orElseThrow();
    }

    public Department getDepartmentWithoutEmployees(Long id) {
        return departmentRepository.findById(id).orElseThrow();
    }
}
