package com.example.demo.dto;

public class EmployeeSummaryDTO {

    private String employeeName;
    private String email;

    public EmployeeSummaryDTO(String employeeName, String email) {
        this.employeeName = employeeName;
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmail() {
        return email;
    }
}
