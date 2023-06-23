package com.example.AuthenticationApplication.proxy;

import com.example.AuthenticationApplication.domain.EmployeeDTO;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EmployeeTaskApplication", url = "http://employee-task-service:9922/")
public interface IEmployeeProxy {
    @DeleteMapping("/api/Admin/removeEmployee/{employeeEmail}")
    ResponseEntity removeEmployee(@PathVariable String employeeEmail) throws EmployeeNotFoundException;

    @PutMapping("/api/Project/updatePassword")
    ResponseEntity<?> updatePassword(@RequestBody EmployeeDTO employeeDTO);

    @PutMapping("/api/Project/updateEmployee")
    ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    @PostMapping("/api/Project/argentTaskNotification/{employeeEmail}")
    ResponseEntity<?> argentTaskNotification(@PathVariable String employeeEmail);
}
