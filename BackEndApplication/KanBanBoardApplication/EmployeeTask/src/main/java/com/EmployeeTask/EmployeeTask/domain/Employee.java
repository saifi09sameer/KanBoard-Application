package com.EmployeeTask.EmployeeTask.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document
public class Employee {
    @Id
    private String employeeEmail;
    private String employeeName;
    private String employeePassword;
    private String employeePhoneNumber;
    private String nationality;
    List<Project> projectList;
}