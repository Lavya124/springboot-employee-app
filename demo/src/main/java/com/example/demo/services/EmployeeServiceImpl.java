package com.example.demo.services;

import com.example.demo.common.Response;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Role;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Response<Employee> createEmployee(Employee employee) {
        Response<Employee> response = new Response<>();

        try {
            if (employeeRepository.existsByEmail(employee.getEmail())) {
                throw new RuntimeException("Employee already exists with email: " + employee.getEmail());
            }

            String encodedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(encodedPassword);

            // Resolve roles
            employee.setRoles(resolveRoles(employee.getRoles()));

            // Save employee
            Employee savedEmployee = employeeRepository.save(employee);

            response.setData(savedEmployee);
            response.setMessage("Employee created successfully.");
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception e) {
            response.setData(null);
            response.setMessage("Internal server error while creating the employee account");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return response;
    }


    @Override
    public Response<Employee> getEmployeeById(Long id) {
        Response<Employee> response = new Response<>();
        try{
            log.trace("Fetching the data of employee with id {}",id);
            Optional<Employee>  getEmployeeById = employeeRepository.findById(id);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Successfully get the information of employee");
            response.setData(getEmployeeById.get());
        } catch (Exception e) {
            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal server error while fetching the account.");
        }
        return response;
    }

    @Override
    public Response<Employee> updateEmployeeById(Long id, Employee updatedEmployee) {
        Response<Employee> response = new Response<>();
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        log.trace("Fetching the data of employee with id {} for updating the info",id);
        try {
            if(optionalEmployee.isPresent()){
                Employee existingEmployee = optionalEmployee.get();

                existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
                existingEmployee.setDoj(updatedEmployee.getDoj());
                existingEmployee.setAddress(updatedEmployee.getAddress());existingEmployee.setId(updatedEmployee.getId());

                existingEmployee.setLeave(updatedEmployee.getLeave());
                existingEmployee.setManagerName(updatedEmployee.getManagerName());
                existingEmployee.setPhoneNo(updatedEmployee.getPhoneNo());
                existingEmployee.setPunchIn(updatedEmployee.getPunchIn());
                existingEmployee.setPunchOut(updatedEmployee.getPunchOut());
                existingEmployee.setSalary(updatedEmployee.getSalary());

                if (updatedEmployee.getRoles() != null && !updatedEmployee.getRoles().isEmpty()) {
                    existingEmployee.setRoles(resolveRoles(updatedEmployee.getRoles()));
                }

                log.debug("After updating the value in db for employee {}",existingEmployee);
                Employee updatedEmploye = employeeRepository.save(existingEmployee);

                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Data is updated successfully");
                response.setData(updatedEmploye);

            }
        } catch (Exception e) {
            response.setData(null);
            response.setMessage("Employee data is updated successfully");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public String deleteEmployee(Long id) {
         try {
             log.trace("Delete the data from db with id {}", id);
             employeeRepository.deleteById(id);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
        return "Employee with ID " + id + " deleted successfully.";
    }

    private Set<Role> resolveRoles(Set<Role> inputRoles) {
        Set<Role> resolvedRoles = new HashSet<>();

        if (inputRoles == null || inputRoles.isEmpty()) {
            return resolvedRoles;
        }

        for (Role role : inputRoles) {
            String roleName = role.getRoleName();
            if (roleName == null || roleName.trim().isEmpty()) {
                throw new IllegalArgumentException("Role name is required");
            }

            Role existing = roleRepository.findByRoleName(roleName.trim())
                    .orElseGet(() -> roleRepository.save(new Role(roleName.trim())));
            resolvedRoles.add(existing);
        }

        return resolvedRoles;
    }

}