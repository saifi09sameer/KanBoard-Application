package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    Map<String,Integer> countNumberOFProjectAndTask();
    List<Project> getTop5Projects();
    String removeEmployee(String employeeEmail) throws EmployeeNotFoundException;
}
