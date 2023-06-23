package com.example.AuthenticationApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Employee {
    @Id
    private String employeeEmail;
    private int employeeID;
    private String employeeName;
    private String gender;
    private String maritalStatus;
    private String employeeDOB;
    private Long employeeAadhaarNumber;
    private String bloodGroup;
    private String employeePhoneNumber;
    private String employeePassword;
    private String joiningDate;
    private String employeeRole;
    private String nationality;
    private String employeeCity;
    private String status;
    private String imageURL;
}