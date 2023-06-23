package com.example.AuthenticationApplication.controller;
import com.example.AuthenticationApplication.domain.Employee;
import com.example.AuthenticationApplication.exception.EmailNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeAlreadyExistsException;
import com.example.AuthenticationApplication.exception.EmployeeListNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;
import com.example.AuthenticationApplication.service.IEmployeeService;
import com.example.AuthenticationApplication.service.IMailService;
import com.example.AuthenticationApplication.service.ITokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/Authentication")  //http://localhost:9911/api/Authentication/
public class EmployeeController {
    private final IEmployeeService iEmployeeService;
    private final ITokenGenerate iTokenGenerate;
    private final IMailService iMailService;
    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService, ITokenGenerate iTokenGenerate,IMailService iMailService) {
        this.iEmployeeService = iEmployeeService;
        this.iTokenGenerate = iTokenGenerate;
        this.iMailService=iMailService;
    }
    //http://localhost:9911/api/Authentication/registerEmployee  [POST][BODY]
    @PostMapping("/registerEmployee")
    public ResponseEntity<?> registerEmployee(@RequestBody  Employee employee) throws EmployeeAlreadyExistsException, EmailNotFoundException {
        if (employee!=null){
            return new ResponseEntity<>(iEmployeeService.registerEmployee(employee), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/loginEmployee  [POST][BODY]
    @PostMapping("/loginEmployee")
    public ResponseEntity<?> loginEmployee(@RequestBody Employee employee) throws EmployeeNotFoundException {
        Employee retriveEmployee = iEmployeeService.loginEmployee(employee.getEmployeeEmail(), employee.getEmployeePassword());
        Map<String,String> employeeMap = iTokenGenerate.generateEmployeeToken(retriveEmployee);
        System.out.println("Login Return Data "+employeeMap);
        return new ResponseEntity<>(employeeMap,HttpStatus.OK);
    }
    //http://localhost:9911/api/Authentication/getStatus  [POST][BODY]
    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(){
        return new ResponseEntity<>(false,HttpStatus.OK);
    }
    //http://localhost:9911/api/Authentication/getEmployeeDetails  [GET][TOKEN]
    @GetMapping("/getEmployeeDetails")
    public ResponseEntity<?> getEmployeeDetails(HttpServletRequest request) throws EmployeeNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iEmployeeService.getEmployeeDetails(employeeEmail),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/sendOTP/
    @GetMapping("/sendOTP/{employeeEmail}")
    public ResponseEntity<?> sendOTP(@PathVariable String employeeEmail) throws EmployeeNotFoundException, EmailNotFoundException {
        if (employeeEmail!=null){
            return new ResponseEntity<>(iMailService.sendOTP(employeeEmail),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/forgotPassword/  [PATH VARIABLE]
    @PutMapping("/forgotPassword/{employeeEmail}/{employeePassword}")
    public ResponseEntity<?> forgotPassword(@PathVariable String employeeEmail,@PathVariable String employeePassword) throws EmployeeNotFoundException {
        String changedPassword = iEmployeeService.forgotPassword(employeeEmail, employeePassword);
        System.out.println("Now Password is this : "+changedPassword);
        return new ResponseEntity<>(changedPassword,HttpStatus.OK);
    }
    //http://localhost:9911/api/Authentication/updateEmployee [TOKEN] [BODY]
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(HttpServletRequest request,@RequestBody Employee employee) throws EmployeeNotFoundException, EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole!=null){
            return new ResponseEntity<>(iEmployeeService.updateEmployee(employee),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/getAllEmployee [TOKEN]
    @GetMapping("/getAllEmployee")
    public ResponseEntity<?> getAllEmployees(HttpServletRequest request) throws EmployeeNotFoundException, EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole!=null && employeeRole.equalsIgnoreCase("Admin")) {
            return new ResponseEntity<>(iEmployeeService.getAllEmployees(),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/deleteEmployee [TOKEN] [BODY]
    @DeleteMapping("/deleteEmployee/{employeeEmail}")
    public ResponseEntity<?> deleteEmployeeByID(HttpServletRequest request ,@PathVariable String employeeEmail) throws EmployeeNotFoundException, EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole!=null && employeeRole.equalsIgnoreCase("Admin")){
            return new ResponseEntity<>(iEmployeeService.deleteEmployee(employeeEmail),HttpStatus.OK);
        }
       throw new EmailNotFoundException();
    }
    //http://localhost:9911/api/Authentication/getAllEmployeesStatus [TOKEN]
    @GetMapping("/getAllEmployeesStatus")
    public ResponseEntity<?> getAllEmployeesStatus(HttpServletRequest request) throws EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iEmployeeService.getAllEmployeesStatus(),HttpStatus.OK);
        }
        throw new EmployeeNotFoundException();
    }
    //http://localhost:9911/api/Authentication/offLineStatus [TOKEN]
    @GetMapping("/offLineStatus")
    public ResponseEntity<?> offLineStatus(HttpServletRequest request) throws EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iEmployeeService.offLineStatus(employeeEmail),HttpStatus.OK);
        }
        throw  new EmployeeNotFoundException();
    }
    //http://localhost:9911/api/Authentication/getEmployeeEmailList [TOKEN]
    @GetMapping("/getEmployeeEmailList")
    public ResponseEntity<?> getEmployeeEmailList(HttpServletRequest request) throws EmployeeNotFoundException, EmailNotFoundException, EmployeeListNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        System.out.println("This is Email that is gettign "+employeeEmail);
        if (employeeEmail!=null){
            return new ResponseEntity<>(iEmployeeService.employeeEmailList(employeeEmail),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
}
