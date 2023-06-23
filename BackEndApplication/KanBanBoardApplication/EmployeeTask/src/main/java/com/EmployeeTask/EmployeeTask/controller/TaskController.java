package com.EmployeeTask.EmployeeTask.controller;

import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.*;
import com.EmployeeTask.EmployeeTask.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/Task/")
public class TaskController {
    ITaskService iTaskService;

    @Autowired
    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    //http://localhost:9922/api/Task/createNewTask/ {projectCreatedEmployee} {projectID} [TOKEN] + [BODY]
    @PutMapping("/createNewTask/{projectCreatedEmployee}/{projectID}")
    public ResponseEntity<?> createNewTask(HttpServletRequest request, @RequestBody Task task, @PathVariable String projectCreatedEmployee, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.createTask(task, employeeEmail, projectCreatedEmployee, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/getAllTasks/{projectID} [TOKEN] + [ProjectID]
    @GetMapping("/getAllTasks/{projectID}")
    public ResponseEntity<?> getAllTasks(HttpServletRequest request, @PathVariable int projectID) throws TaskNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.getAllTasks(employeeEmail, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/deleteTask/ {projectID} [TOKEN] + [ProjectID]
    @DeleteMapping("/deleteTask/{projectCreatedEmployee}/{projectID}/{taskID}")
    public ResponseEntity<?> deleteTask(HttpServletRequest request, @PathVariable String projectCreatedEmployee, @PathVariable int projectID, @PathVariable int taskID) throws EmailNotFoundException, ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        System.out.println("projectCreatedEmployee" + projectCreatedEmployee + "\n" + " " + projectID + " " + "\n" + "taskID" + taskID);
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.deleteTask(projectCreatedEmployee, projectID, taskID,employeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9922/api/Task/updateTask/ {projectID} [TOKEN] + [ProjectID]
    @PutMapping("/updateTask/{projectCreatedBY}/{projectID}/{taskID}/{updatedTaskDescription}")
    public ResponseEntity<?> updateTask(HttpServletRequest request, @PathVariable String projectCreatedBY, @PathVariable int projectID, @PathVariable int taskID, @PathVariable String updatedTaskDescription) throws EmailNotFoundException, ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.updateTask(updatedTaskDescription, projectCreatedBY, projectID, taskID),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/updateTaskStage/ {Path Variables} [TOKEN]
    @PutMapping("/updateTaskStage/{projectCreatedEmployee}/{projectID}/{taskID}/{taskUpdatedStage}")
    public ResponseEntity<?> updateTaskStage(HttpServletRequest request,
                                             @PathVariable String projectCreatedEmployee,
                                             @PathVariable int projectID,
                                             @PathVariable int taskID,
                                             @PathVariable String taskUpdatedStage) throws EmailNotFoundException, ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.updateTaskStage(projectCreatedEmployee, projectID, taskID, taskUpdatedStage), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/assignTask/ {Path Variables} [TOKEN]
    @PutMapping("/assignTask/{projectCreatedBy}/{projectID}/{taskID}/{assignEmployee}")
    public ResponseEntity<?> assignTask(HttpServletRequest request,
                                        @PathVariable String projectCreatedBy,
                                        @PathVariable int projectID,
                                        @PathVariable int taskID,
                                        @PathVariable String assignEmployee) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException, EmailNotFoundException {

        String employeeEmail = (String) request.getAttribute("employeeEmail");
        System.out.println("Token Email " + employeeEmail);
        System.out.println("projectCreatedBy " + projectCreatedBy);
        System.out.println("projectID " + projectID);
        System.out.println("taskID " + taskID);
        System.out.println("assignEmployee" + assignEmployee);
        if (employeeEmail != null) {
           // System.out.println("This is response here for assignted task : " + iTaskService.assignTask(projectCreatedBy, projectID, taskID, assignEmployee));
            return new ResponseEntity<>(iTaskService.assignTask(projectCreatedBy, projectID, taskID, assignEmployee), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/getAssignEmployeeList/ {Path Variables} [TOKEN]
    @GetMapping("/getAssignEmployeeList/{projectCreatedBy}/{projectID}/{taskID}")
    public ResponseEntity<?> getAssignEmployeeList(HttpServletRequest request,
                                                   @PathVariable String projectCreatedBy,
                                                   @PathVariable int projectID,
                                                   @PathVariable int taskID) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException, EmailNotFoundException {

        String employeeEmail = (String) request.getAttribute("employeeEmail");
        System.out.println("Ye data piche ja rha hai isko dekho ek bar" + iTaskService.getAssignEmployeeList(projectCreatedBy, projectID, taskID));
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.getAssignEmployeeList(projectCreatedBy, projectID, taskID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/removeAssignEmployee/ {Path Variables} [TOKEN]
    @DeleteMapping("/removeAssignEmployee/{projectCreatedBy}/{projectID}/{taskID}/{removeEmployee}")
    public ResponseEntity<?> removeAssignEmployee(HttpServletRequest request,
                                                  @PathVariable String projectCreatedBy,
                                                  @PathVariable int projectID,
                                                  @PathVariable int taskID,
                                                  @PathVariable String removeEmployee) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.removeAssignEmployee(projectCreatedBy, projectID, taskID, removeEmployee), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/getEmployeesList/ {Path Variables} [TOKEN]
    @PostMapping("/getEmployeesList/{projectCreatedBY}/{projectID}")
    public ResponseEntity<?> getEmployessEmailListResponseEntity(HttpServletRequest request, @RequestBody List<String> employeeEmailList, @PathVariable String projectCreatedBY, @PathVariable int projectID) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException, EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.getEmployeeEmailList(employeeEmailList, projectCreatedBY, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/getAllDeletedTasks/ {Path Variables} [TOKEN]
    @GetMapping("/getAllDeletedTasks/{projectCreatedBy}/{projectID}")
    public ResponseEntity<?> getAllDeletedTasks(HttpServletRequest request, @PathVariable String projectCreatedBy, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.getAllDeletedTasks(projectCreatedBy, employeeEmail, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }

    //http://localhost:9922/api/Task/deleteAllDeletedTasks {Path Variables} [TOKEN]
    @DeleteMapping("/deleteAllDeletedTasks/{projectCreatedBY}/{projectID}")
    public ResponseEntity<?> deleteAllDeletedTasks(HttpServletRequest request, @PathVariable String projectCreatedBY, @PathVariable int projectID) throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            return new ResponseEntity<>(iTaskService.deleteAllDeletedTasks(projectCreatedBY, employeeEmail, projectID), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9922/api/Task/filteredEmployeeListForAssign {Path Variables} [TOKEN]
    @PutMapping("/filteredEmployeeListForAssign/{projectCreatedBy}/{projectID}/{taskID}")
    public ResponseEntity<?> filteredEmployeeListForAssign(HttpServletRequest request, @RequestBody Set<String> employeeList,@PathVariable String projectCreatedBy,@PathVariable int projectID, @PathVariable int taskID) throws EmailNotFoundException, ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iTaskService.filteredEmployeeListForAssign(projectCreatedBy,projectID,taskID,employeeList),HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
}
