package com.EmployeeTask.EmployeeTask.controller;

import com.EmployeeTask.EmployeeTask.exception.EmailNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Admin")
public class AdminController {
    private final IAdminService iAdminService;


    @Autowired
    public AdminController(IAdminService iAdminService) {
        this.iAdminService = iAdminService;
    }

    //http://localhost:9922/api/Admin/countNumberOFProjectAndTask  [TOKEN]
    @GetMapping("/countNumberOFProjectAndTask")
    public ResponseEntity<?> countNumberOFProjectAndTask(HttpServletRequest request) throws EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole.equalsIgnoreCase("Admin")) {
            return new ResponseEntity<>(iAdminService.countNumberOFProjectAndTask(), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Admin/getTop5Projects  [TOKEN]
    @GetMapping("/getTop5Projects")
    public ResponseEntity<?> getTop5Projects(HttpServletRequest request) throws EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole != null) {
            return new ResponseEntity<>(iAdminService.getTop5Projects(), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Admin/removeEmployee  [TOKEN]
    @DeleteMapping("/removeEmployee/{employeeEmail}")
    public ResponseEntity<?> removeEmployee(@PathVariable String employeeEmail) throws  EmployeeNotFoundException {
        return new ResponseEntity<>(iAdminService.removeEmployee(employeeEmail), HttpStatus.OK);
    }
}
