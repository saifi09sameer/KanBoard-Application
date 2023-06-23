package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.TaskNotFoundException;

import java.util.List;
import java.util.Set;

public interface ITaskService {
    boolean createTask(Task task, String employeeEmail, String projectCreatedEmployee, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException;

    List<Task> getAllTasks(String employeeEmail, int projectID) throws TaskNotFoundException;
    boolean updateTask(String updatedTaskDescription,String projectCreatedBY,int projectID,int taskID) throws EmployeeNotFoundException,ProjectNotFoundException,TaskNotFoundException;
    boolean deleteTask(String projectCreatedEmployee, int projectID, int taskID, String employeeEmail) throws TaskNotFoundException, ProjectNotFoundException, EmployeeNotFoundException;

    boolean updateTaskStage(String projectCreatedEmployee, int projectID, int taskID, String taskUpdatedStage) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException;

    boolean assignTask(String projectCreatedBy, int projectID, int taskID, String assignEmployee) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException;

    Set<String> filteredEmployeeListForAssign(String projectCreatedBY,int projectID,int taskID,Set<String> employeeList) throws EmployeeNotFoundException,ProjectNotFoundException,TaskNotFoundException;

    Set<String> getAssignEmployeeList(String projectCreatedBy, int projectID, int taskID) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException;

    boolean removeAssignEmployee(String projectCreatedBy, int projectID, int taskID, String removeEmployee) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException;

    List<String> getEmployeeEmailList(List<String> employeeEmailList, String projectCreatedBY, int projectID) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException;

    List<Task> getAllDeletedTasks(String projectCreatedBy, String employeeEmail, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException;

    boolean deleteAllDeletedTasks(String projectCreatedBY, String employeeEmail, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException;
}