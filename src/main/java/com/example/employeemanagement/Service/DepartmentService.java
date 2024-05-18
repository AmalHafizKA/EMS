package com.example.employeemanagement.Service;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id).orElseThrow();
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setCreationDate(updatedDepartment.getCreationDate());
        existingDepartment.setHead(updatedDepartment.getHead());
        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department not found with id : "+id);
        }
        departmentRepository.deleteById(id);
    }

    public Page<Department> listDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public Department getDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id).orElseThrow();
    }

    public Department getDepartmentWithoutEmployees(Long id) {
        return departmentRepository.findById(id).orElseThrow();
    }
}