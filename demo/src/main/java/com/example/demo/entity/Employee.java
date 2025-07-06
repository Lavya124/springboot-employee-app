package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "This field must not be empty")
    private String employeeName;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password must not be empty")
    private String password;

    private String address;
    private long phoneNo;

    @NotNull(message = "DOJ must not be empty")
    private long doj;

    private String managerName;
    private int leave;
    private long punchIn;
    private long punchOut;
    private double salary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getDoj() {
        return doj;
    }

    public void setDoj(long doj) {
        this.doj = doj;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public long getPunchIn() {
        return punchIn;
    }

    public void setPunchIn(long punchIn) {
        this.punchIn = punchIn;
    }

    public long getPunchOut() {
        return punchOut;
    }

    public void setPunchOut(long punchOut) {
        this.punchOut = punchOut;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNo=" + phoneNo +
                ", doj=" + doj +
                ", managerName='" + managerName + '\'' +
                ", leave=" + leave +
                ", punchIn=" + punchIn +
                ", punchOut=" + punchOut +
                ", salary=" + salary +
                ", roles=" + roles +
                '}';
    }
}
