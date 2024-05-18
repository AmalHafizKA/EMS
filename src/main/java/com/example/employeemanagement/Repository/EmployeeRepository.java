package com.example.employeemanagement.Repository;

import com.example.employeemanagement.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new map(e.id as id, e.name as name) FROM Employee e")
    List<Map<String, Object>> findAllNamesAndIds();

    Employee findByNameAndDateOfBirth(String name, LocalDate dateOfBirth);
}