package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProjectManager {
    public static List<String> getEmployeeEmailList(List<Employee> employeeList, List<String> employeeEmailList) throws EmployeeNotFoundException {
        if (employeeList.isEmpty() && employeeEmailList.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        List<String> finalEmployeeEmailList = new ArrayList<>();
        int numberOfProjectAccess = 5;

        for (String employeeEmail : employeeEmailList) {
            int count = 0;
            for (Employee employee : employeeList) {
                for (Project project : employee.getProjectList()) {
                    Set<String> projectEmployees = project.getEmployeeList();
                    if (projectEmployees == null) {
                        break;
                    }
                    for (String employeeEmailData : projectEmployees) {
                        if (employeeEmail.equalsIgnoreCase(employeeEmailData)) {
                            count++;
                            break;
                        }
                    }
                }
            }
            if (numberOfProjectAccess >= count) {
                count = 0;
                finalEmployeeEmailList.add(employeeEmail);

            } else {
                count = 0;
            }
        }
        return finalEmployeeEmailList;
    }

    static public List<Project> getAllProjectsBasedOnAssign(List<Employee> employeeList, String employeeEmail) throws EmployeeNotFoundException {
        List<Project> finalProjectList = new ArrayList<>();
        if (employeeList == null) {
            throw new EmployeeNotFoundException();
        }
        for (Employee employee : employeeList) {
            List<Project> projectList = employee.getProjectList();
            for (Project project : projectList) {
                Set<String> stringList = project.getEmployeeList();
                if (stringList == null) {
                    break;
                } else {
                    for (String employeeEmil : stringList) {
                        if (employeeEmil.equalsIgnoreCase(employeeEmail)) {
                            finalProjectList.add(project);
                        }
                    }
                }
            }
        }
        return finalProjectList;
    }

    public static Project findProjectBasedOnProjectID(List<Employee> employeeList, int projectID, String employeeEmail) throws ProjectNotFoundException {
        if (employeeList == null || projectID == 0) {
            throw new ProjectNotFoundException();
        }
        Project projectData = null;

        for (Employee employee : employeeList) {
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        projectData = project;
                    }
                }
            }
        }

        if (projectData.getCreatedBY().equalsIgnoreCase(employeeEmail)) {
            return projectData;
        } else {
            List<Task> taskListData = new ArrayList<>();
            List<Task> taskList = projectData.getTaskList();
            if (taskList != null) {
                for (Task task : taskList) {
                    if (task.getTaskCreatedBY().equalsIgnoreCase(employeeEmail)) {
                        taskListData.add(task);
                    }
                }
            }
            if (taskList!=null){
                for (Task task:taskList) {
                    Set<String> assignemployeeList = task.getAssignTaskEmployees();
                    if (assignemployeeList!=null){
                        if (assignemployeeList.contains(employeeEmail)){
                            taskListData.add(task);
                        }
                    }
                }
                projectData.setTaskList(taskListData);
                return projectData;
            }
        }
        throw new ProjectNotFoundException();
    }


}
