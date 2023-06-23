package com.EmployeeTask.EmployeeTask.proxy;

import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
import com.EmployeeTask.EmployeeTask.exception.EmployeeAlreadyExistsException;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Authentication-Application", url = "http://employee-authentication-service:9911/")
public interface IEmployeeProxy {
    @PostMapping("/api/Authentication/registerEmployee")
    ResponseEntity<?> registerEmployee(@RequestBody EmployeeDTO customerDTO) throws EmployeeAlreadyExistsException;



}
