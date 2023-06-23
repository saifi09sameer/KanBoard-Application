package com.example.AuthenticationApplication.service;

import com.example.AuthenticationApplication.domain.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class TokenGenerate implements ITokenGenerate{

    @Override
    public Map<String, String> generateEmployeeToken(Employee employee) {
        Map<String,String> result= new HashMap<>();
        Map<String,Object> userData = new HashMap<>();
        userData.put("employeeName",employee.getEmployeeName());
        userData.put("employeeEmail",employee.getEmployeeEmail());
        userData.put("employeeAddress",employee.getEmployeeCity());
        userData.put("employeePhoneNumber",employee.getEmployeePhoneNumber());
        userData.put("employeeRole",employee.getEmployeeRole());
        String myToken= Jwts.builder().setClaims(userData)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,"secretKeyData")
                .compact();
        result.put("Token",myToken);
        result.put("employeeName",employee.getEmployeeName());
        result.put("employeeEmail",employee.getEmployeeEmail());
        result.put("employeeCity",employee.getEmployeeCity());
        result.put("employeePhoneNumber",employee.getEmployeePhoneNumber());
        result.put("employeeRole",employee.getEmployeeRole());
        result.put("Message","("+employee.getEmployeeName()+")"+" login successful");
        return result;
    }
}
