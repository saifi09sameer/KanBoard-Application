package com.example.AuthenticationApplication.service;

import com.example.AuthenticationApplication.domain.Employee;

import java.util.Map;

public interface ITokenGenerate {
    Map<String,String> generateEmployeeToken(Employee employee);
}
