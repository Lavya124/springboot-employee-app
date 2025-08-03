package com.example.demo.services;

import com.example.demo.common.Response;
import com.example.demo.dto.EmployeeSummaryDTO;
import com.example.demo.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    Response<Employee> createEmployee(Employee employee);
    Response<Employee> getEmployeeById(Long id);

    Response<Employee> updateEmployeeById(Long id, Employee updatedEmployee);
    String deleteEmployee(Long id);

    public Map<String, List<EmployeeSummaryDTO>> getEmployeesGroupedByManager();
    public List<EmployeeSummaryDTO> getEmployeesByManager(String managerName);

}
