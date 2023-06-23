package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.exception.*;

import java.util.List;
import java.util.Set;

public interface IProjectService {
    Employee insertNewEmployee(Employee employee) throws EmployeeAlreadyExistsException, EmployeeNotFoundException;
    Employee updatePassword(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;
    Employee updateEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    List<Project> getProjects(String employeeEmail) throws ProjectNotFoundException, EmployeeNotFoundException;
    boolean deleteProject(String employeeEmail,int projectID) throws  ProjectNotFoundException, EmployeeNotFoundException;
    boolean createProject(String employeeEmail,Project project) throws EmployeeNotFoundException;
    List<String> getEmployeeEmailList(List<String> employeeEmailList) throws ProjectNotFoundException, EmployeeNotFoundException;
    Set<String> filteredEmployeeListForAssign(String projectCreatedBY, int projectID, Set<String> employeeList) throws EmployeeNotFoundException,ProjectNotFoundException;

    boolean assignEmployeeToProjects(String assignEmployeeEmail, int projectID, String employeeEmail) throws ProjectNotFoundException,EmployeeNotFoundException;
    Set<String> getAssignEmployeeList(String employeeEmail,int projectID) throws EmployeeNotFoundException, ProjectNotFoundException, AssignEmployeeNotFoundException;
    boolean deleteAssignEmployee(String employeeEmail,int projectID,String assignEmployeeEmail) throws ProjectNotFoundException,AssignEmployeeNotFoundException;

    Project findProjectBasedOnProjectID(int projectID,String employeeEmail) throws ProjectNotFoundException;
    boolean argentTaskNotification(String employeeEmail) throws EmployeeNotFoundException, ProjectNotFoundException;

}
