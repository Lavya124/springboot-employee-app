package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

        public Optional<Employee> findById(Long id);

        Optional<Employee> findByEmail(String email);


        boolean existsByEmail(String email);

        // Optional: Fetch all employees ordered by managerName
        List<Employee> findAllByOrderByManagerNameAsc();

        // OR: Get all employees for a specific manager
        List<Employee> findByManagerName(String managerName);

}
