package com.example.AuthenticationApplication.service;

import com.example.AuthenticationApplication.domain.Employee;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;

import javax.mail.MessagingException;

public interface IMailService {


    int sendOTP(String employeeEmail) throws EmployeeNotFoundException;
    void sendRegistrationConfirmation(Employee employee);
}
