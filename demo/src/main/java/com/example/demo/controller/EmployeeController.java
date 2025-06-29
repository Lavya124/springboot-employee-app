package com.example.demo.controller;

import com.example.demo.common.Response;
import com.example.demo.entity.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final Logger log = LogManager.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Response<Employee>> createEmployee(@RequestBody Employee employee){
        try{
            log.trace("For creating the data of employee in controller {}",employee);
            Response<Employee> response = employeeService.createEmployee(employee);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Employee>> getEmployee(@PathVariable Long id){
        try {
            Response<Employee> response = employeeService.getEmployeeById(id);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Employee>> updatedEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){
        try {
            Response<Employee> updated = employeeService.updateEmployeeById(id, updatedEmployee);
            return ResponseEntity.status(updated.getStatus()).body(updated);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        try {
            employeeService.deleteEmployee(id);
            String msg =  "Employee is successfully deleted";

            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}



