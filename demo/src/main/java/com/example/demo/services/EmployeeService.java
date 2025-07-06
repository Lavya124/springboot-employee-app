package com.example.demo.services;

import com.example.demo.common.Response;
import com.example.demo.entity.Employee;

public interface EmployeeService {

    Response<Employee> createEmployee(Employee employee);
    Response<Employee> getEmployeeById(Long id);

    Response<Employee> updateEmployeeById(Long id, Employee updatedEmployee);
    String deleteEmployee(Long id);
}
