package com.example.AuthenticationApplication.service;

import com.example.AuthenticationApplication.domain.Employee;
import com.example.AuthenticationApplication.exception.EmailNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeAlreadyExistsException;
import com.example.AuthenticationApplication.exception.EmployeeListNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;

import java.util.List;

public interface IEmployeeService {
    Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    Employee loginEmployee(String employeeEmail, String employeePassword) throws EmployeeNotFoundException;
    String forgotPassword(String employeeEmail,String employeePassword) throws EmployeeNotFoundException;
    Employee getEmployeeDetails(String employeeEmail) throws EmployeeNotFoundException;
    Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;
    List<Employee> getAllEmployees() throws EmployeeNotFoundException;
    boolean deleteEmployee(String employeeEmail) throws EmployeeNotFoundException;
    List<String> getAllEmployeesStatus() throws EmployeeNotFoundException;
    boolean offLineStatus(String employeeEmail) throws EmployeeNotFoundException;
    List<String> employeeEmailList(String employeeEmail) throws EmployeeNotFoundException, EmployeeListNotFoundException;
}
