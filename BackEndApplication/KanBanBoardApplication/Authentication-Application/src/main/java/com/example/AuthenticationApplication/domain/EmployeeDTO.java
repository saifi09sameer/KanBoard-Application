package com.example.AuthenticationApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String employeeEmail;
    private String employeeName;
    private String employeePassword;
    private String employeePhoneNumber;
    private String nationality;
}