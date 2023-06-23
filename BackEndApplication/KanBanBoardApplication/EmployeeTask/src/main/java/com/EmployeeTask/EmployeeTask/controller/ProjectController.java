package com.EmployeeTask.EmployeeTask.controller;

import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.exception.*;
import com.EmployeeTask.EmployeeTask.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/Project/")
//http://localhost:7879/api/Project/
public class ProjectController {
    IProjectService iProjectService;

    @Autowired
    public ProjectController(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }

    //http://localhost:9922/api/Project/registerEmployee  [BODY]
    @PostMapping("/registerEmployee")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExistsException, EmployeeNotFoundException {
        return new ResponseEntity<>(iProjectService.insertNewEmployee(employee), HttpStatus.OK);
    }

    //http://localhost:9922/api/Project/updateEmployee  [BODY]
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        return new ResponseEntity<>(iProjectService.updateEmployee(employeeDTO), HttpStatus.OK);
    }

    //http://localhost:9922/api/Project/updateEmployee  [BODY]
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Employee employee = iProjectService.updatePassword(employeeDTO);
        System.out.println("Updated Employee is " + employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //http://localhost:9922/api/Project/getProjects [TOKEN]
    @GetMapping("/getProjects")
    public ResponseEntity<?> getProjects(HttpServletRequest request) throws ProjectNotFoundException, EmailNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.getProjects(employeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9922/api/Project/getNoOFProjects [PATH]
    @GetMapping("/getNoOFProjects/{employeeEmail}")
    public ResponseEntity<?> getNoOFProjects(HttpServletRequest request ,@PathVariable String employeeEmail) throws ProjectNotFoundException, EmployeeNotFoundException, EmailNotFoundException {
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole != null && employeeRole.equalsIgnoreCase("Admin")){
            return new ResponseEntity<>(iProjectService.getProjects(employeeEmail),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/deleteProject/* [TOKEN]
    @DeleteMapping("/deleteProject/{projectID}")
    public ResponseEntity<?> deleteProject(HttpServletRequest request, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.deleteProject(employeeEmail, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/createProject  [TOKEN] [BODY]
    @PutMapping("/createProject")
    public ResponseEntity<?> createProject(HttpServletRequest request, @RequestBody Project project) throws EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.createProject(employeeEmail, project), HttpStatus.CREATED);
        }
        throw new EmployeeNotFoundException();
    }

    //http://localhost:9922/api/Project/getEmployeesList  [TOKEN] [BODY]
    @PostMapping("/getEmployeesList")
    public ResponseEntity<?> getEmployessEmailListResponseEntity(HttpServletRequest request, @RequestBody List<String> employeeEmailList) throws ProjectNotFoundException, EmailNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.getEmployeeEmailList(employeeEmailList), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/assignEmployeeToProjects  [TOKEN] [BODY] [PATH VARIABLE]
    @PutMapping("/assignEmployeeToProjects/{projectID}/{assignEmployeeEmail}")
    public ResponseEntity<?> assignEmployeeToProjects(HttpServletRequest request, @PathVariable String assignEmployeeEmail, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.assignEmployeeToProjects(assignEmployeeEmail, projectID, employeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/findProjectBasedOnProjectID [PATH VARIABLE] [TOKEN]
    @GetMapping("/findProjectBasedOnProjectID/{projectID}")
    public ResponseEntity<?> findProjectBasedOnProjectID(HttpServletRequest request, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.findProjectBasedOnProjectID(projectID, employeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/getAssignEmployeeList/{projectID}  [TOKEN] [PATH VARIABLE]
    @GetMapping("/getAssignEmployeeList/{projectID}")
    public ResponseEntity<?> getAssignEmployeeList(HttpServletRequest request, @PathVariable int projectID) throws ProjectNotFoundException, AssignEmployeeNotFoundException, EmployeeNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.getAssignEmployeeList(employeeEmail, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Project/deleteAssignEmployee/{projectID}/{assignEmployeeEmail}  [TOKEN] [PATH VARIABLE]
    @DeleteMapping("/deleteAssignEmployee/{projectID}/{assignEmployeeEmail}")
    public ResponseEntity<?> deleteAssignEmployee(HttpServletRequest request, @PathVariable int projectID, @PathVariable String assignEmployeeEmail) throws ProjectNotFoundException, AssignEmployeeNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iProjectService.deleteAssignEmployee(employeeEmail, projectID, assignEmployeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9922/api/Project/filteredEmployeeListForAssign/{projectID}/   [TOKEN] [BODY]
    @PutMapping("/filteredEmployeeListForAssign/{projectID}")
    public ResponseEntity<?> filteredEmployeeListForAssign(HttpServletRequest request, @PathVariable int projectID ,@RequestBody Set<String> employeeEmailList) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iProjectService.filteredEmployeeListForAssign(employeeEmail,projectID,employeeEmailList),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9922/api/Project/argentTaskNotification/  [TOKEN]
    @PostMapping("/argentTaskNotification/{employeeEmail}")
    public ResponseEntity<?> argentTaskNotification(@PathVariable String employeeEmail) throws ProjectNotFoundException, EmployeeNotFoundException, EmailNotFoundException {
        if (employeeEmail!=null){
            return new ResponseEntity<>(iProjectService.argentTaskNotification(employeeEmail),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }


}
