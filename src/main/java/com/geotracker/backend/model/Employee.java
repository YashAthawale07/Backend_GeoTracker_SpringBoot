package com.geotracker.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employees")
public class Employee {

    @Id
    private String id;

    private String empId;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String role;   // EMPLOYEE / ADMIN
    private String post;   // e.g. Software Engineer, Manager

    public Employee() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPost() { return post; }
    public void setPost(String post) { this.post = post; }
}