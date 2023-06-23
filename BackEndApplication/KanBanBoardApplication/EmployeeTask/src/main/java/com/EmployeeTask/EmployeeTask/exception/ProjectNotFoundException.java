package com.EmployeeTask.EmployeeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project Not Found With This ID")
public class ProjectNotFoundException extends Exception{
}
